package Controllers;

import java.util.ArrayList;
import Models.*;

public class AuthorController {

    private MySQLDatabase dbController;

    public AuthorController(){
        dbController = Injector.getDbController();
    }

    public ArrayList<Author> getAllAuthors(){
        String query = "SELECT author_id,first_name,last_name FROM authors";

        ArrayList<String> params = new ArrayList<>();


            ArrayList<ArrayList<String>> authorResults = dbController.getData(query,params);

            ArrayList<Author> results = new ArrayList<>();
            for(ArrayList<String> anAuthor: authorResults){
                results.add(new Author(anAuthor));
            }

            return results;


    }

    public Author getAuthor(int id){
        String query = "SELECT author_id,first_name,last_name FROM authors WHERE author_id = ?;";

        ArrayList<String> params = new ArrayList<>();
        params.add(id+"");

        ArrayList<ArrayList<String>> authorResults = dbController.getData(query,params);
        return new Author(authorResults.get(0));

    }

    public Author getAuthor(String fname, String lname){
        String query = "SELECT author_id,first_name,last_name FROM authors WHERE " + 
            "first_name = ? AND last_name = ?;";

        ArrayList<String> params = new ArrayList<>();
        params.add(fname);
        params.add(lname);

        ArrayList<ArrayList<String>> results = dbController.getData(query,params);
        return ( results != null ? new Author(results.get(0)) : null );
    }

    public boolean addAuthor(Author author){
        String query = "INSERT INTO authors(first_name,last_name) VALUES(?,?);";

        ArrayList<String> params = new ArrayList<>();
        params.add(author.getfName());
        params.add(author.getlName());

        boolean check = dbController.setData(query,params);
        return check;
    }

}
