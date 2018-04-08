import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BookController {

    private MySQLDatabase dbController;

    // public methods

    /**
     * Method used to add a new physical copy to the db
     * @param book  copy to be added
     * @return
     */
    public boolean addBook(Book book){

        int book_detail_id = getBookDetailId(book);
        if(book_detail_id == -1) return false;

        BookDetailsController bookDetailsController = null;         //figure out an injector
        BookDetails detail = bookDetailsController.getABook(book_detail_id);

        if(detail == null) return false;


        return addToDb(detail);
    }

    /**
     * Method used to remove books from db
     * @param book
     * @return
     */
    public boolean removeBook(Book book){
        String query = "DELETE FROM book_copies WHERE book_id = ?; ";

        ArrayList<String> params = new ArrayList<>();
        params.add(book.getId()+"");


        return dbController.setData(query,params);


    }

    public ArrayList<Object[]> getAllLoanDetails(){

        return null;
    }

    public Object[] getLoanDetail(Book book,int userId){

        return null;
    }

    public ArrayList<Object[]> getAllReservations(){

        return null;
    }

    public ArrayList<Object[]> getReservationDetail(BookDetails details,int userId)
    {

        return null;
    }


    /**
     * Method used to loan books
     * @param book
     * @param userId
     * @return
     */
    public boolean loanBook(Book book, int userId){
        int book_id = getBookDetailId(book);

        if(book_id == -1) return false;

        Calendar dueDate = Calendar.getInstance();
        dueDate.setTimeInMillis(14 * 86400000);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String timestamp = dateFormat.format(dueDate);

        String query = "INSERT INTO loans(book_id,copy_id,user_id,due_date) VALUES(?,?,?,?);";

        ArrayList<String> params = new ArrayList<>();
        params.add(book_id+"");
        params.add(book.getId()+"");
        params.add(userId+"");
        params.add(timestamp);

        return dbController.setData(query,params);
    }

    /**
     * Method used to return book
     * @param book
     * @param userId
     * @return
     */
    public boolean returnBook(Book book, int userId){
        int book_id = getBookDetailId(book);
        if(book_id == -1) return false;

        String query = "DELETE FROM loans WHERE copy_id = ? AND book_id = ? AND user_id = ?;";

        ArrayList<String> params = new ArrayList<>();
        params.add(book.getId()+"");
        params.add(book_id+"");
        params.add(userId+"");

        return dbController.setData(query,params);
    }


    public boolean placeReservation(BookDetails bookDetails, int userId){

        int book_detail_id = bookDetails.getBook_id();

        Calendar currentTime = Calendar.getInstance();


        String query =  "INSERT into RESERVATIONS(book_id,user_id, date_reserved)"+
                        "VALUES(?,?,?)";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(currentTime.getTime());


        ArrayList<String> params = new ArrayList<String>();
        params.add(book_detail_id+"");
        params.add(userId+"");
        params.add(date);

        return dbController.setData(query,params);
    }

    /**
     * Method used to get all books
     * @return  arraylist containing books
     */
    public ArrayList<Book> getAllBooks(){
        String query = "SELECT copy_id, " +
                "book_details.isbn, book_details.title, book_details.release_date," +
                "book_details.num_copies, " +
                "authors.first_name, authors.last_name, genres.name, genres.description"+
                "FROM book_copies"+
                    "JOIN book_details USING(book_id)"+
                    "JOIN authors USING (book_details.author_id)"+
                    "JOIN genres USING (book_details.genre_id);";

        try {
            ArrayList<ArrayList<String>> results = dbController.getData(query,new ArrayList<String>());
            ArrayList<Book> bookCollection = new ArrayList<>();

            for(ArrayList<String> aBook:results){
                bookCollection.add(new Book(aBook));
            }

            return bookCollection;
        } catch (DLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Book getABook(Book id){
        String query = "SELECT copy_id, " +
                "book_details.isbn, book_details.title, book_details.release_date," +
                "book_details.num_copies, " +
                "authors.first_name, authors.last_name, genres.name, genres.description"+
                "FROM book_copies"+
                "JOIN book_details USING(book_id)"+
                "JOIN authors USING (book_details.author_id)"+
                "JOIN genres USING (book_details.genre_id)"+
                "WHERE copy_id = ?";

        ArrayList<String> params = new ArrayList<>();
        params.add(id+"");

        try {
            ArrayList<ArrayList<String>> results = dbController.getData(query,params);

            return new Book(results.get(0));

        } catch (DLException e) {
            //TODO: handle this
        }

        return null;
    }


    // private methods

    /**
     * Important!!! Method has to get a book details object with a valid ID,
     * Suggest loading the BookDetail collection from the database first!!!!!
     * @param details
     * @return
     */
    private boolean addToDb(BookDetails details){

        String query = "INSERT INTO book_copies(book_id)"+
                       "VALUES(?);";

        ArrayList<String> params = new ArrayList<>();
        params.add(details.getBook_id()+"");

        boolean check = dbController.setData(query,params);
        return check;
    }

    private int getBookDetailId(Book book){
        String query = "SELECT book_id FROM book_details WHERE isbn = ? AND title = ?";

        ArrayList<String> params = new ArrayList<>();

        params.add(book.getISBN()+"");
        params.add(book.getTitle());

        try {
            ArrayList<ArrayList<String>> results = dbController.getData(query,params);

            return Integer.parseInt(results.get(0).get(0));
        } catch (DLException e) {
            //TODO: handle this
        }

        return -1;
    }
}
