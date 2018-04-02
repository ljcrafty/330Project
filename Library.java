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
            String query = "SELECT book_id, title, release_date, num_copies, authors.first_name, " + 
                "authors.last_name, genres.name, genres.description FROM books JOIN authors USING " + 
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

                    this.addUserReservation( book, Integer.parseInt(temp.get(1)) );
                }
                else
                {
                    Book book = this.getBook( Integer.parseInt(temp.get(0)) );
                    book.setDueDate( temp.get(3) );

                    this.addUserLoan( book, Integer.parseInt(temp.get(1)) );
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
    public Book getBook( int id )
    {
        if( this.books.containsKey( (Integer) id) )
        {
            return this.books.get( (Integer) id );
        }

        return null;
    }

    public User getUser( int id )
    {
        if( this.users.containsKey( (Integer) id ) )
        {
            return this.users.get( (Integer) id );
        }

        return null;
    }

    public ArrayList<Book> getUserLoans( int id )
    {
        if( this.loans.containsKey( (Integer) id ) )
        {
            return this.loans.get( (Integer) id );
        }

        return null;
    }

    public ArrayList<Book> getUserReservations( int id )
    {
        if( this.reservations.containsKey( (Integer) id ) )
        {
            return this.reservations.get( (Integer) id );
        }

        return null;
    }

    public int getNumCopies( int id )
    {
        if( this.copiesAvailable.containsKey( (Integer) id ) )
        {
            return this.copiesAvailable.get( (Integer) id );
        }

        return -1;
    }

    //setters
    //TODO: modify database with the calls
    public boolean addUserLoan( Book book, int id )
    {
        if( this.loans.containsKey( (Integer) id ) && this.getNumCopies(id) > 0 )
        {
            ArrayList<Book> temp = this.loans.get( (Integer) id );

            //can't take out more than one copy of the same book
            if( temp.contains( book ) || this.reservations.get( (Integer) id ).contains(book) ||
                !this.setNumCopies( id, this.getNumCopies(id) - 1 ) ) //updates number of available books
            {
                return false;
            }

            temp.add( book );
            this.loans.put( (Integer) id, temp );

            return true;
        }

        return false;
    }

    public boolean addUserReservation( Book book, int id )
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

            temp.add( book );
            this.reservations.put( (Integer) id, temp );

            return true;
        }
        
        return false;
    }

    public boolean removeUserLoan( Book book, int id )
    {
        if( !this.loans.containsKey( (Integer) id ) )
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
        return false;
    }

    public boolean removeUserReservation( Book book, int id )
    {
        if( !this.reservations.containsKey( (Integer) id ) )
        {
            ArrayList<Book> temp = this.reservations.get( (Integer) id );

            temp.remove( book );
            this.reservations.put( (Integer) id, temp );

            return true;
        }
        return false;
    }

    public boolean setNumCopies( int id, int num )
    {
        if( this.copiesAvailable.containsKey( (Integer) id ) && num >= 0 )
        {
            this.copiesAvailable.put( (Integer) id, (Integer) num );
            return true;
        }

        return false;
    }

    //stuff about getting rid of users/books authenticating, etc.
    
    public boolean addBook( Book book )
    {
        if( this.books.containsKey(book.getId()) )
        {
            return false;
        }

        this.books.put( book.getId(), book );
        this.copiesAvailable.put( book.getId(), book.getNumCopies() );
        return true;
    }

    public Book addBook( String title, String author_fname, String author_lname, String genre_name, 
        String genre_desc, int num_copies, int id, Calendar release_date )
    {
        Book temp = new Book( title, author_fname, author_lname, genre_name, genre_desc, num_copies, 
            id, release_date );
        
        if( this.addBook(temp) )
        {
            return temp;
        }

        return null;
    }

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
}