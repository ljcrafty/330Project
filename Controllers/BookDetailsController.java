package Controllers;

import java.util.ArrayList;
import Models.*;

public class BookDetailsController {

    private MySQLDatabase dbController;

    public BookDetailsController(){
        Injector.getDbController();
    }

    public ArrayList<BookDetails> getAllBooks(){
        String query = "SELECT book_id, isbn, title, release_date, num_copies, authors.first_name,"+
                "authors.last_name, genres.name, genres.description"+
                "FROM book_details"+
                "JOIN authors USING (author_id)"+
                "JOIN genres USING (genre_id)";

            ArrayList<ArrayList<String>> result = dbController.getData(query,new ArrayList<String>());
            ArrayList<BookDetails> detailsResult = new ArrayList<>();

            for(ArrayList<String> aDetail: result){
                detailsResult.add(new BookDetails(aDetail));
            }

            return detailsResult;

    }

    public BookDetails getABook(int id){
        String query = "SELECT book_id, isbn, title, release_date, num_copies, authors.first_name,"+
                       "authors.last_name, genres.name, genres.description"+
                       "FROM book_details"+
                       "JOIN authors USING (author_id)"+
                       "JOIN genres USING (genre_id)"+
                       "WHERE book_id = ?";

        ArrayList<String> params = new ArrayList<>();
        params.add(id+"");


            ArrayList<ArrayList<String>> result = dbController.getData(query,params);

            return new BookDetails(result.get(0));

    }



    public boolean addABook(BookDetails bookDetails, int authorId, int genreId){
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
