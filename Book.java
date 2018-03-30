import java.util.Calendar;

public class Book
{
    private String title, author_fname, author_lname, genre_name, genre_desc;
    private int num_copies;
    private Calendar release_date;

    public Book()
    {
        this.title          = "";
        this.author_fname   = "";
        this.author_lname   = "";
        this.genre_name     = "";
        this.genre_desc     = "";
        this.num_copies     = 0;
        this.release_date   = Calendar.getInstance();
    }

    public Book( String title, String author_fname, String author_lname, String genre_name, 
        String genre_desc, int num_copies, Calendar release_date )
    {
        this.title = title;
        this.author_fname = author_fname;
        this.author_lname = author_lname;
        this.genre_name = genre_name;
        this.genre_desc = genre_desc;
        this.num_copies = num_copies;
        this.release_date = release_date;
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

    public Calendar getReleaseDate()
    {
        return this.release_date;
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

    public void setReleaseDate( Calendar date )
    {
        this.release_date = date;
    }
}