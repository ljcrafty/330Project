import java.util.*;

public class Book
{
    private String title, author_fname, author_lname, genre_name, genre_desc;
    private int num_copies, id, isbn;
    private Calendar release_date, due_date;

    public Book()
    {
        this.title          = "";
        this.author_fname   = "";
        this.author_lname   = "";
        this.genre_name     = "";
        this.genre_desc     = "";
        this.num_copies     = 0;
        this.id             = 0;
        this.isbn           = 0;
        this.release_date   = Calendar.getInstance();
        this.due_date       = null;
    }

    public Book( String title, String author_fname, String author_lname, String genre_name, 
        String genre_desc, int num_copies, int id, int isbn, Calendar release_date )
    {
        this.title = title;
        this.author_fname = author_fname;
        this.author_lname = author_lname;
        this.genre_name = genre_name;
        this.genre_desc = genre_desc;
        this.num_copies = num_copies;
        this.id = id;
        this.isbn = isbn;
        this.release_date = release_date;
        this.due_date = null;
    }

    public Book( ArrayList<String> vals )
    {
        Calendar temp = Calendar.getInstance();
        String date = vals.get(3).split( " " )[0];
        int yr = Integer.parseInt( date.split("-")[0] );
        int mo = Integer.parseInt( date.split("-")[1] ) - 1;
        int day = Integer.parseInt( date.split("-")[2] );
        temp.set( yr, mo, day ); //YYYY-MM-DD

        this.title = vals.get(2);
        this.author_fname = vals.get(5);
        this.author_lname = vals.get(6);
        this.genre_name = vals.get(7);
        this.genre_desc = vals.get(8);
        this.num_copies = Integer.parseInt( vals.get(4) );
        this.id = Integer.parseInt( vals.get(0) );
        this.isbn = Integer.parseInt( vals.get(1) );
        this.release_date = temp;
        this.due_date = null;
    }

    //getters
    public String getTitle()
    {
        return this.title;
    }

    public String getAuthorFName()
    {
        return this.author_fname;
    }        
    
    public String getAuthorLName()
    {
        return this.author_lname;
    }
                
    public String getGenreName()
    {
        return this.genre_name;
    }
                
    public String getGenreDesc()
    {
        return this.genre_desc;
    }

    public int getNumCopies()
    {
        return this.num_copies;
    }

    public int getId()
    {
        return this.id;
    }

    public int getISBN()
    {
        return this.isbn;
    }

    public Calendar getReleaseDate()
    {
        return this.release_date;
    }

    public Calendar getDueDate()
    {
        return this.due_date;
    }

    //setters
    public void setTitle( String title )
    {
        this.title = title;
    }

    public void setAuthorFName( String name )
    {
        this.author_fname = name;
    }        
    
    public void setAuthorLName( String name )
    {
        this.author_lname = name;
    }
                
    public void setGenreName( String name )
    {
        this.genre_name = name;
    }
                
    public void setGenreDesc( String desc )
    {
        this.genre_desc = desc;
    }

    public void setNumCopies( int num )
    {
        this.num_copies = num;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public void setISBN( int isbn )
    {
        this.isbn = isbn;
    }

    public void setReleaseDate( Calendar date )
    {
        this.release_date = date;
    }

    public void setDueDate( Calendar date )
    {
        this.due_date = date;
    }

    public void setDueDate( String date )
    {
        Calendar temp = Calendar.getInstance();
        int yr = Integer.parseInt( date.split("-")[0] );
        int mo = Integer.parseInt( date.split("-")[1] ) - 1;
        int day = Integer.parseInt( date.split("-")[2] );
        temp.set( yr, mo, day );

        this.due_date = temp;
    }
}