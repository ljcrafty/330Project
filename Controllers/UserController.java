package Controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import Models.*;
import com.auth0.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;


public class UserController {


    //key = token
    //value = privilege level
    private HashMap<String,Integer> loggedUsers;
    private MySQLDatabase dbController;
    private String key = "s0m3r4nd0mk3y";
    
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
//         this.loggedUsers = new HashMap<String, Integer>();
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

//                return data.get(0).get(0);
                try {
                    Algorithm algorithm = Algorithm.HMAC256(key);
                    String token = JWT.create()
                            .withIssuer("auth0")
                            .sign(algorithm);

                    loggedUsers.put(token,temp.getRole());

                    return token;
                } catch (UnsupportedEncodingException exception){
                    return "unexpected_err_UTF";
                } catch (JWTCreationException exception){
                    return "unexpected_err_creation";
                }
            }
            else{
                return "invalid password";
            }
        }

    }

    public User getUser(int id)
    {
        String query = "SELECT user_id, username, password, first_name, last_name, date_of_birth, role_id " + 
            "FROM users JOIN user_role USING user_id WHERE user_id = ?;";

        ArrayList<String> params = new ArrayList<>();
        params.add(id+"");

        ArrayList<ArrayList<String>> results = dbController.getData(query,params);
        
        if( results != null )
        {
            return new User(results.get(0));
        }
        
        return null;
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

        return addToDatabase(newUser);
    }

    public boolean authenticate(String token, int required_privilege){

        try {
            Algorithm algorithm = Algorithm.HMAC256(key);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
        } catch (UnsupportedEncodingException exception){
            //UTF-8 encoding not supported
        } catch (JWTVerificationException exception){
            return false;                                   //token is invalid
        }

        if(!loggedUsers.containsKey(token)) return false;

        if(loggedUsers.get(token)<required_privilege) return false;
        else return true;


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

            check = dbController.setData(query, params);

            return check;
        }

        return false;
    }


}
