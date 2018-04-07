import java.util.ArrayList;

public class AuthorController {

    private MySQLDatabase dbController;

    public ArrayList<Author> getAllAuthors(){
        String query = "SELECT author_id,first_name,last_name FROM;";

        ArrayList<String> params = new ArrayList<>();

        try {
            ArrayList<ArrayList<String>> authorResults = dbController.getData(query,params);

            ArrayList<Author> results = new ArrayList<>();
            for(ArrayList<String> anAuthor: authorResults){
                results.add(new Author(anAuthor));
            }

            return results;
        } catch (DLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Author getAuthor(int id){
        String query = "SELECT author_id,first_name,last_name FROM authors WHERE id = ?;";

        ArrayList<String> params = new ArrayList<>();
        params.add(id+"");

        try {
            ArrayList<ArrayList<String>> authorResults = dbController.getData(query,params);
            return new Author(authorResults.get(0));
        } catch (DLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean addAuthor(Author author){
        String query = "INSERT INTO(first_name,last_name) VALUES(?,?);";

        ArrayList<String> params = new ArrayList<>();
        params.add(author.getfName());
        params.add(author.getlName());

        boolean check = dbController.setData(query,params);
        return check;
    }

}
