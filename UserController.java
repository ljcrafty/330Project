package Controllers;

import java.util.ArrayList;
import java.util.HashMap;



public class UserController {


    //key = token
    //value = privilege level
    private HashMap<String,Integer> loggedUsers;
    private Controllers.DatabaseController dbController;

    // PUBLIC METHODS

    /**
     * Method used to log in into system
     * @param username
     * @param password
     * @return
     */
    public String login(String username, String password){
        String query = "SELECT username, password, role_id FROM users JOIN user_role USING (user_id) WHERE username = ?";
        ArrayList<String> params = new ArrayList<>();
        params.add(username);

        ArrayList<ArrayList<String>> data = dbController.getData(query,params);
        if (data == null) return null;

        else{
            User temp = new User(data.get(0).get(0),data.get(0).get(1), data.get(0).get(2));

        }
        return "";
    }

    /**
     * Method used to log out
     * @param token
     */
    public void logout(String token){

        loggedUsers.remove(token);
    }

    public boolean authorize(String token){

        return false;
    }

    public boolean addUser(String username, String password){

        return false;
    }



    // PRIVATE METHODS
    private boolean authenticate(String token, id required_privilege){

        return false;
    }
}
