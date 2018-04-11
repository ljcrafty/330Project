import java.util.ArrayList;
import java.util.HashMap;



public class UserController {


    //key = token
    //value = privilege level
    private HashMap<String,Integer> loggedUsers;
    private MySQLDatabase dbController;
    
    public UserController()
    {
         this.dbController = new MySQLDatabase("mysql", "3306", "localhost",
            "Library", "library", "V3ry5ecretC0de");
         this.loggedUsers = new HashMap<String, Integer>();
    }

    // PUBLIC METHODS

    /**
     * Method used to log in into system
     * @param username
     * @param password
     * @return returns JWToken on success, "no user" if the username is not found and "invalid password" if the password is wrong.
     */
    public String login(String username, String password){
        String query = "SELECT username, password, role_id FROM users JOIN user_role USING (user_id) WHERE username = ?";
        ArrayList<String> params = new ArrayList<>();
        params.add(username);

        ArrayList<ArrayList<String>> data = null;
        try {
            data = dbController.getData(query,params);
        } catch (DLException e) {
            //TODO: log error
        }
        if (data == null) return "no user";

        else{
            User temp = new User(data.get(0).get(0),data.get(0).get(1), data.get(0).get(2));
            if(BCrypt.checkpw(password,temp.getPasswordHash())){
                //TODO: find something similar to JWT
                return "token_placeholder";
            }
            else{
                return "invalid password";
            }
        }

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

    /**
     * Method used to add new user to database
     * @param newUser
     * @return
     */
    public boolean addUser(User newUser){
        String plaintext = newUser.getPasswordHash();

        newUser.setPasswordHash(BCrypt.hashpw(plaintext, BCrypt.gensalt(12)));

        addToDatabase(newUser);

        return false;
    }



    // PRIVATE METHODS
    private boolean authenticate(String token, int required_privilege){
        //TODO: find something similar to JWT.
        return false;
    }

    /**
     Adds a user to the Library and database
     @param user - the user to add
     @return whether or not the addition was successful
     */
    private boolean addToDatabase( User user )
    {
        /**
         * query = "SELECT user_id, username, password, first_name, last_name, age, role_id FROM users JOIN user_role USING (user_id)";
         */
        String query = "INSERT INTO users(username,password,first_name,last_name,age,role_id) VALUES (?,?,?,?,?,?);";
        boolean check = dbController.setData(query,user.getUserParameters(false));              //not sure if user ID is autoincremented or not.

        return check;
    }


}
