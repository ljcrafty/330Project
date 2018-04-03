import java.util.*;

//takes care of the data side of actions performed in the library
public class Library
{
    private HashMap<Integer, Book> books;
    private HashMap<Integer, User> users;
    private HashMap<Integer, ArrayList<Book>> loans;
    private HashMap<Integer, ArrayList<Book>> reservations;
    private HashMap<Integer, Integer> copiesAvailable;
    private MySQLDatabase db;

    public Library( String dbType, String port, String ip, String db, 
        String user, String pass )
    {
        this.books = new HashMap<Integer, Book>();
        this.users = new HashMap<Integer, User>();
        this.loans = new HashMap<Integer, ArrayList<Book>>();
        this.reservations = new HashMap<Integer, ArrayList<Book>>();
        this.copiesAvailable = new HashMap<Integer, Integer>();
        this.db = new MySQLDatabase( dbType, port, ip, db, user, pass );
    }

    /**
        Grabs data from the database and loads the Library with that data
        ** ALL PREVIOUS DATA IS FLUSHED, IF CONNECTION FAILS, LIBRARY WILL BE EMPTY **
     */
    public void loadData()
    {
        this.books = new HashMap<Integer, Book>();
        this.users = new HashMap<Integer, User>();
        this.loans = new HashMap<Integer, ArrayList<Book>>();
        this.reservations = new HashMap<Integer, ArrayList<Book>>();
        this.copiesAvailable = new HashMap<Integer, Integer>();

        try
        {
            this.db.connect();
            
            //books
            String query = "SELECT book_id, isbn, title, release_date, authors.first_name, " + 
                "authors.last_name, genres.name, genres.description FROM book JOIN authors USING " + 
                "(author_id) JOIN genres USING (genre_id)";
            ArrayList<String> params = new ArrayList<String>();

            ArrayList<ArrayList<String>> data = db.getData( query, params );

            for( int i = 0; i < data.size(); i++ )
            {
                Book temp = new Book( data.get(i) );

                this.books.put( temp.getId(), temp );
                this.copiesAvailable.put( temp.getId(), temp.getNumCopies() );
            }

            //users
            query = "SELECT user_id, username, password, first_name, last_name, " + 
                "age, role_id FROM users JOIN user_role USING (user_id)";

            data = db.getData( query, params );

            for( int i = 0; i < data.size(); i++ )
            {
                User temp = new User( data.get(i) );

                this.users.put( temp.getId(), temp );
            }

            //loans and reservations
            query = "SELECT book_id, user_id, reserved, due_date FROM borrowed_book";
            
            data = db.getData( query, params );

            for( int i = 0; i < data.size(); i++ )
            {
                ArrayList<String> temp = data.get(i);

                //reserved, not borrowed
                if( temp.get(2) == "true" )
                {
                    Book book = this.getBook( Integer.parseInt(temp.get(0)) );

                    this.addReservation( book, Integer.parseInt(temp.get(1)) );
                }
                else
                {
                    Book book = this.getBook( Integer.parseInt(temp.get(0)) );
                    book.setDueDate( temp.get(3) );

                    this.addLoan( book, Integer.parseInt(temp.get(1)) );
                }
            }
            this.db.close();
        }
        catch( DLException e )
        {
            return;
        }
    }

    //getters
    /**
        Gets a book from the library
        @param id - the id of the book to retreive
        @return the requested book or null if it does not exist
     */
    public Book getBook( int id )
    {
        if( this.books.containsKey( (Integer) id) )
        {
            return this.books.get( (Integer) id );
        }

        return null;
    }

    /**
        Gets a user from the library
        @param id - the id of the user to retreive
        @return the requested user or null if it does not exist
     */
    public User getUser( int id )
    {
        if( this.users.containsKey( (Integer) id ) )
        {
            return this.users.get( (Integer) id );
        }

        return null;
    }

    /**
        Gets the list of books a user has loaned from the library
        @param id - the id of the user to retreive loaned books for
        @return an ArrayList of the loaned Book models for the user or null if there are none
     */
    public ArrayList<Book> getUserLoans( int id )
    {
        if( this.loans.containsKey( (Integer) id ) )
        {
            return this.loans.get( (Integer) id );
        }

        return null;
    }

    /**
        Gets the list of books a user has reserved from the library
        @param id - the id of the user to retreive reserved books for
        @return an ArrayList of the reserved Book models for the user or null if there are none
     */
    public ArrayList<Book> getUserReservations( int id )
    {
        if( this.reservations.containsKey( (Integer) id ) )
        {
            return this.reservations.get( (Integer) id );
        }

        return null;
    }


    //setters
    /**
        Adds a loan to the library and database
        @param book - the book to be loaned to the user
        @param id - the user that will be loaning the book
        @return whether or not the loan was successful
     */
    public boolean addLoan( Book book, int id )
    {
        if( this.loans.containsKey( (Integer) id ) && this.getNumCopies(id) > 0 )
        {
            ArrayList<Book> temp = this.loans.get( (Integer) id );

            String query = "INSERT INTO borrowed_book VALUES( ?, ?, false, ? )";
            ArrayList<String> params = new ArrayList<String>();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            params.add( Integer.toString(book.getId()) );
            params.add( Integer.toString(id) );
            params.add( format.format(Calendar.getInstance().add( Calendar.DATE, 7 )) );//books are due a week from loan

            if( db.setData( query, params ) )
            {
                temp.add( book );
                this.loans.put( (Integer) id, temp );

                return true;
            }
        }
        return false;
    }

    /**
        Adds a reservation to the library and database
        @param book - the book to be reserved to the user
        @param id - the user that will be reserving the book
        @return whether or not the reservation was successful
     */
    public boolean addReservation( Book book, int id )
    {
        //can't reserve unless there are no available copies
        if( this.reservations.containsKey( (Integer) id ) && this.getNumCopies(id) == 0 )
        {
            ArrayList<Book> temp = this.reservations.get( (Integer) id );

            //can't take out more than one copy of the same book
            if( temp.contains( book ) || this.loans.get( (Integer) id ).contains(book) )
            {
                return false;
            }

            String query = "INSERT INTO borrowed_book SET book_id = ?, user_id = ?, reserved = true ";
            ArrayList<String> params = new ArrayList<String>();
            params.add( Integer.toString(book.getId()) );
            params.add( Integer.toString(id) );

            if( db.setData( query, params ) )
            {
                temp.add( book );
                this.reservations.put( (Integer) id, temp );

                return true;
            }
        }
        return false;
    }

    /**
        Removes a loan to the library and database
        @param book - the book to be returned from the user
        @param id - the user that will be returning the book
        @return whether or not the return was successful
     */
    public boolean removeLoan( Book book, int id )
    {
        if( !this.loans.containsKey( (Integer) id ) )
        {
            String query = "DELETE FROM borrowed_book WHERE user_id = ? AND book_id = ?";
            ArrayList<String> params = new ArrayList<String>();
            params.add( Integer.toString(id) );
            params.add( Integer.toString(book.getId()) );

            if( db.setData( query, params ) )
            {
                ArrayList<Book> temp = this.loans.get( (Integer) id );
                temp.remove( book );
                this.loans.put( (Integer) id, temp );

                if( book.getNumCopies() >= this.getNumCopies(book.getId()) )
                {
                    this.setNumCopies( book.getNumCopies() );
                    return true;
                }

                if( !this.setNumCopies( id, this.getNumCopies(id) + 1 ) ) //updates number of available books
                {
                    return false;
                }

                return true;
            }
        }
        return false;
    }

    /**
        Removes a reservation from the library and database
        @param book - the book to remove the reservation for
        @param id - the user that will be removing the reservation
        @return whether or not the removal was successful
     */
    public boolean removeReservation( Book book, int id )
    {
        if( !this.reservations.containsKey( (Integer) id ) )
        {
            String query = "DELETE FROM borrowed_book WHERE user_id = ? AND book_id = ?";
            ArrayList<String> params = new ArrayList<String>();
            params.add( Integer.toString(id) );
            params.add( Integer.toString(book.getId()) );

            if( db.setData( query, params ) )
            {
                ArrayList<Book> temp = this.reservations.get( (Integer) id );

                temp.remove( book );
                this.reservations.put( (Integer) id, temp );

                return true;
            }
        }
        return false;
    }


    //TODO: stuff about authenticating, etc.
    //TODO: modify database here too

    /**
        Adds a book to the Library and database
        @param book - the book to add
        @return whether or not the addition was successful
     */
    public boolean addBook( Book book )
    {
        if( this.books.containsKey(book.getId()) )
        {
            return false;
        }

        this.books.put( book.getId(), book );
        return true;
    }

    /**
        Adds a book to the Library and database
        @param title - the title of the book to add
        @param author_fname - the author's first name of the book to add
        @param author_lname - the author's last name of the book to add
        @param genre_name - the genre name of the book to add
        @param genre_desc - the genre description of the book to add
        @param id - the book id of the book to add
        @param isbn - the ISBN number of the book to add
        @param release_date - the release date of the book to add
        @return the book that was created from the params, or null if addition was unsuccessful
     */
    public Book addBook( String title, String author_fname, String author_lname, String genre_name, 
        String genre_desc, int id, int isbn, Calendar release_date )
    {
        Book temp = new Book( title, author_fname, author_lname, genre_name, genre_desc, 
            id, isbn, release_date );
        
        if( this.addBook(temp) )
        {
            return temp;
        }

        return null;
    }

    /**
        Adds a user to the Library and database
        @param user - the user to add
        @return whether or not the addition was successful
     */
    public boolean addUser( User user )
    {
        if( this.users.containsKey(user.getId()) )
        {
            return false;
        }

        this.users.put( user.getId(), user );
        this.loans.put( user.getId(), new ArrayList<Book>() );
        this.reservations.put( user.getId(), new ArrayList<Book>() );

        return true;
    }

    /**
        Adds a user to the Library and database
        @param username - the username of the user to add
        @param pass - the password of the user to add
        @param fname - the first name of the user to add
        @param lname - the last name of the user to add
        @param role - the role id of the user to add
        @param age - the age of the user to add
        @param id - the user id of the user to add
        @return the user that was created from the params, or null if addition was unsuccessful
     */
    public User addUser( String username, String pass, String fname, String lname, int role, 
        int age, int id )
    {
        User temp = new User( username, pass, fname, lname, role, age, id );
        
        if( this.addUser(temp) )
        {
            return temp;
        }

        return null;
    }

    /**
        Removes a user from the Library and database
        @param id - the id of the user to remove
        @return whether or not the removal was successful
     */
    public boolean removeUser( int id )
    {
        if( !this.users.containsKey( (Integer) id ) )
        {
            return false;
        }

        this.users.remove( (Integer) id );
        this.loans.remove( (Integer) id );
        this.reservations.remove( (Integer) id );

        return true;
    }

    /**
        Removes a book from the Library and database
        @param id - the id of the book to remove
        @return whether or not the removal was successful
     */
    public boolean removeBook( int id )
    {
        if( !this.books.containsKey( (Integer) id ) )
        {
            return false;
        }

        this.books.remove( (Integer) id );
        this.copiesAvailable.remove( (Integer) id );
        Book book = this.getBook( id );
        
        for( Integer i : this.loans.keySet() )
        {
            ArrayList<Book> temp = this.loans.get(i);

            if( temp.contains( book ) )
            {
                this.removeLoan(book);
            }
        }

        for( Integer i : this.reservations.keySet() )
        {
            ArrayList<Book> temp = this.reservations.get(i);

            if( temp.contains( book ) )
            {
                this.removeReservation(book);
            }
        }

        return true;
    }
}