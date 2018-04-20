package Controllers;

import java.util.ArrayList;
import Models.Genre;

public class GenreController {

    MySQLDatabase dbController;

    public GenreController(){
        this.dbController = Injector.getDbController();
    }

    public Genre getGenre(int id){
        String query = "SELECT genre_id,name,description FROM genres WHERE genre_id = ?";

        ArrayList<String> params = new ArrayList<>();
        params.add(id+"");


        ArrayList<ArrayList<String>> genreResults = dbController.getData(query,params);
        return new Genre(genreResults.get(0));
    }

    public Genre getGenre(String name){
        String query = "SELECT genre_id,name,description FROM genres WHERE name = ?";

        ArrayList<String> params = new ArrayList<>();
        params.add(name+"");


        ArrayList<ArrayList<String>> results = dbController.getData(query,params);
        return ( results != null ? new Genre(results.get(0)) : null );

    }

    public ArrayList<Genre> getAllGenres(){
        String query = "SELECT genre_id,name,description FROM genres";

            ArrayList<ArrayList<String>> genreResults = dbController.getData(query,new ArrayList<>());

            ArrayList<Genre> results = new ArrayList<>();
            for(ArrayList<String> aGenre: genreResults){
                results.add(new Genre(aGenre));
            }

            return results;

    }

    public boolean addGenre(String name, String description){
        ArrayList<String> params = new ArrayList<>();
        params.add(name);
        params.add(description);

        String query = "INSERT INTO genres(name,description) VALUES (?,?);";

        boolean check = dbController.setData(query,params);

        return check;
    }
}
