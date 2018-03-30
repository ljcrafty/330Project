import java.util.*;

//takes care of the data side of actions performed in the library
public class Library
{
    private ArrayList<Book> books;
    private ArrayList<User> users;
    private HashMap<User, ArrayList<Book>> loans;
    private HashMap<User, ArrayList<Book>> reservations;
    private HashMap<Book, Integer> copiesAvailable;

    public Library()
    {
        this.books = new ArrayList<Book>();
        this.users = new ArrayList<User>();
        this.loans = new HashMap<User, ArrayList<Book>>();
        this.reservations = new HashMap<User, ArrayList<Book>>();
        this.copiesAvailable = new HashMap<Book, Integer>();
    }

    public void loadData( ArrayList<Book> books, ArrayList<User> users )
    {
        this.books = books;
        this.users = users;
        this.copiesAvailable = new HashMap<Book, Integer>();

        for( int i = 0; i < books.size(); i++ )
        {
            this.copiesAvailable.put( books.get(i), books.get(i).getNumCopies() );
        }
    }

    //stuff about new users and new books and getting rid of users/books and loaning/reserving stuff
    //authenticating, etc.
}