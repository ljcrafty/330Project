import java.util.ArrayList;

public class BookDetailsController {

    private MySQLDatabase dbController;
    public ArrayList<BookDetails> getAllBooks(){

    }

    public BookDetails getABook(int id){

    }

    public boolean addABook(BookDetails bookDetails,int authorId,int genreId){
        String query = "INSERT INTO book_details(isbn,title,release_date,num_copies,author_id,genre_id)"+
                 "VALUES(?,?,?,?,?,?)";

        ArrayList<String> params = new ArrayList<>();
        params.add(bookDetails.getBook_id()+"");
        params.add(bookDetails.getTitle());
        params.add(bookDetails.getReleaseDate().toString());
        params.add(bookDetails.getNumCopies()+"");
        params.add(authorId+"");
        params.add(genreId+"");

        return dbController.setData(query,params);
    }
}
