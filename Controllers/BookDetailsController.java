package Controllers;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import Models.*;

public class BookDetailsController {

    private MySQLDatabase dbController;

    public BookDetailsController(){
        this.dbController = Injector.getDbController();
    }

    public ArrayList<BookDetails> getAllBooks(){
        String query = "SELECT book_id, isbn, title, release_date, num_copies, authors.first_name,"+
                "authors.last_name, genres.name, genres.description "+
                "FROM book_details "+
                "JOIN authors USING (author_id) "+
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
                       "authors.last_name, genres.name, genres.description "+
                       "FROM book_details "+
                       "JOIN authors USING (author_id) "+
                       "JOIN genres USING (genre_id) "+
                       "WHERE book_id = ?";

        ArrayList<String> params = new ArrayList<>();
        params.add(id+"");


        ArrayList<ArrayList<String>> result = dbController.getData(query,params);

        if( result == null || result.size() == 0 )
        {
            return null;
        }

        return new BookDetails(result.get(0));
    }



    public boolean addABook(BookDetails bookDetails, int authorId, int genreId){
        String query = "SELECT MAX(book_id) FROM book_details";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<ArrayList<String>> data = dbController.getData(query, new ArrayList<String>());
        int num = Integer.parseInt(data.get(0).get(0)) + 1;
        
        if( data == null )
        {
            System.out.println("no data");
            return false;
        }

        try
        {
            dbController.startTrans();

            query = "INSERT INTO book_details(book_id, isbn,title,release_date,num_copies,author_id,genre_id)"+
                    "VALUES(?,?,?,?,?,?,?)";

            ArrayList<String> params = new ArrayList<>();
            params.add( Integer.toString(num) );
            params.add( Long.toString(bookDetails.getIsbn()) );
            params.add(bookDetails.getTitle());
            params.add( format.format(bookDetails.getReleaseDate().getTime()) );
            params.add(bookDetails.getNumCopies()+"");
            params.add(authorId+"");
            params.add(genreId+"");

            if(dbController.setData(query,params))
            {
                for( int i = 1; i <= bookDetails.getNumCopies(); i++ )
                {
                    query = "INSERT INTO book_copies(book_id, copy_id) VALUES(?, ?)";
                    params = new ArrayList<String>();
                    params.add( Integer.toString(num) );
                    params.add( Integer.toString(i) );

                    if( !dbController.setData(query,params) )
                    {
                        dbController.rollbackTrans();
                        return false;
                    }
                }
                dbController.endTrans();
                return true;
            }
            
            dbController.rollbackTrans();
            return false;
        }
        catch( DLException e )
        {
            return false;
        }
    }
}
