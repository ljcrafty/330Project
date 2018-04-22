package Controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.SimpleDateFormat;
import Models.*;


public class UserController {


    //key = token
    //value = privilege level
    private HashMap<Integer,Integer> loggedUsers;
    private MySQLDatabase dbController;
    
    public UserController(){
            this.dbController = Injector.getDbController();
            
            try
            {
                this.dbController.connect();
            }
            catch( DLException e )
            {
                System.out.println("Error connecting to the database");
            }
            this.loggedUsers = new HashMap<Integer, Integer>();
    }

    // PUBLIC METHODS

    /**
     * Method used to log in into system
     * @param username
     * @param password
     * @return returns JWToken on success, "no user" if the username is not found and "invalid password" if the password is wrong.
     */
    public String login(String username, String password){
        String query = "SELECT user_id, username, password, role_id FROM users JOIN user_role USING (user_id) WHERE username = ?";
        ArrayList<String> params = new ArrayList<>();
        params.add(username);

        ArrayList<ArrayList<String>> data = dbController.getData(query,params);
        
        if (data == null || data.size() == 0) return "no user";

        else{
            User temp = new User(Integer.parseInt(data.get(0).get(0)),
                data.get(0).get(1), data.get(0).get(2), data.get(0).get(3));

            if(BCrypt.checkpw(password,temp.getPasswordHash())){
                
                loggedUsers.put(temp.getId(),temp.getRole());
                return temp.getId() + "";
            }
            else{
                return "invalid password";
            }
        }

    }

    public User getUser(int id)
    {
        String query = "SELECT user_id, username, password, first_name, last_name, date_of_birth, role_id " + 
            "FROM users JOIN user_role USING (user_id) WHERE user_id = ?;";

        ArrayList<String> params = new ArrayList<>();
        params.add(id+"");

        ArrayList<ArrayList<String>> results = dbController.getData(query,params);
        
        if( results != null && results.size() > 0 )
        {
            return new User(results.get(0));
        }
        
        return null;
    }

    public ArrayList<User> getAllUsers(){
        String query = "SELECT user_id, username, password, first_name, last_name, date_of_birth, role_id " +
                "FROM users JOIN user_role USING (user_id);";

        ArrayList<String> params = new ArrayList<>();


        ArrayList<ArrayList<String>> results = dbController.getData(query,params);
        ArrayList<User> users = new ArrayList<>();

        for(ArrayList<String> anResult: results){
            users.add(new User(anResult));
        }

        return users;
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
     * Update a user's information in the database
     * @param newUser a User object that contains the new data for the user and has the correct user id
     * @return whether or not the update was successful
     */
    public boolean updateUser(User newUser)
    {
        String query = "UPDATE users SET username = ?, password = ?, first_name = ?, " + 
            "last_name = ?, date_of_birth = ? WHERE user_id = ?";
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<String> params = new ArrayList<String>();
        params.add( newUser.getUsername() );
        params.add( BCrypt.hashpw(newUser.getPasswordHash(), BCrypt.gensalt(12)) );
        params.add( newUser.getFName() );
        params.add( newUser.getLName() );
        params.add( format.format(newUser.getDOB().getTime()) );
        params.add( Integer.toString(newUser.getId()) );

        return dbController.setData( query, params );
    }

    /**
     * Method used to add new user to database
     * @param newUser
     * @return
     */
    public boolean addUser(User newUser){
        String plaintext = newUser.getPasswordHash();

        newUser.setPasswordHash(BCrypt.hashpw(plaintext, BCrypt.gensalt(12)));

        return addToDatabase(newUser);
    }

    public boolean authenticate(String token, int required_privilege){

        if(!loggedUsers.containsKey(Integer.parseInt(token)))
        {
            return false;
        }

        if(loggedUsers.get(Integer.parseInt(token))>required_privilege)
        {
            return false;
        }
        
        return true;


    }

    // PRIVATE METHODS

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
        //get next id and set it
        String query = "SELECT MAX(user_id) FROM users";
        ArrayList<ArrayList<String>> data = dbController.getData(query, new ArrayList<String>());
        
        if( data == null )
        {
            System.out.println("no data");
            return false;
        }

        user.setId( Integer.parseInt(data.get(0).get(0)) + 1 );

        query = "INSERT INTO users(user_id,username,password,first_name,last_name,date_of_birth) VALUES (?,?,?,?,?,?);";
        boolean check = dbController.setData(query, user.getUserParameters(true));

        if( check )
        {
            query = "INSERT INTO user_role(user_id,role_id) VALUES (?,?);";
            ArrayList<String> params = new ArrayList<String>();
            params.add(user.getId() + "");
            params.add(user.getRole() + "");

            return dbController.setData(query, params);
        }

        return false;
    }

    public ArrayList<User> searchUsers(String[] parameters){
        boolean checkParams = false;

        for(int i = 1; i< parameters.length; i++){
            if(!parameters[i].equals("")) checkParams = true;
        }
        //fn,ln,uid,username
        String[] headers = new String[]{
                "first_name",
                "last_name",
                "user_id",
                "username"
        };

        String query = "SELECT user_id, username, password, first_name, last_name, date_of_birth, role_id " +
                "FROM users JOIN user_role USING (user_id)";


        ArrayList<String> params = new ArrayList<>();
        if(checkParams){
            query += " WHERE ";
            for(int i = 1; i< parameters.length-1; i++){
                if(!parameters[i].equals("")){
                    query += String.format("%s = ? AND ",headers[i-1]);
                    params.add(parameters[i]);
                }

            }
            //last entry
            if(!parameters[parameters.length-1].equals("")) {
                query += String.format("%s = ?;", headers[headers.length - 1]);
                params.add(parameters[parameters.length - 1]);
            }
            else {
                query = query.substring(0,query.length()-4);
            }
        }

        ArrayList<ArrayList<String>> dbResults = dbController.getData(query,params);
        ArrayList<User> retVal = new ArrayList<>();
        for(ArrayList<String> entry: dbResults){
            retVal.add(new User(entry));
        }

        return retVal;
    }



}
