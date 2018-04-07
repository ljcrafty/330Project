import java.util.ArrayList;

public class BookController {

    // public methods
    public boolean addBook(Book book){

        return true;
    }

    public boolean removeBook(Book book){

        return true;
    }

    public boolean loanBook(Book book, int userId){

        return true;
    }

    public boolean placeReservation(Book book){

        return true;
    }

    public boolean getCatalog(String[] parameter_headers, ArrayList<String> parameters){

        return true;
    }


    // private methods

    private boolean addToDb(Book book, boolean isDetail){

        if(isDetail){           //if it is not actual copy, just generalization

        }
        else{

        }

        return true;
    }

    private Book getBookDetails(int book_details_id){

    }


}
