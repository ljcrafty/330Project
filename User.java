import java.util.*;
import java.text.SimpleDateFormat;

public class User
{
    private String username, passwordHash, fname, lname;
    private int id, role;
    private Calendar date_of_birth;

    public User()
    {
        this.username = "";
        this.passwordHash = "";
        this.fname = "";
        this.lname = "";
        this.role = 0;
        this.date_of_birth = Calendar.getInstance();
        this.id = 0;
    }

    public User( String username, String pass, String fname, String lname, int role, 
        Calendar date, int id )
    {
        this.username = username;
        this.passwordHash = pass;
        this.fname = fname;
        this.lname = lname;
        this.role = role;
        this.date_of_birth = date;
        this.id = id;
    }

    public User( ArrayList<String> vals )
    {
        int yr = Integer.parseInt(vals.get(5).split("-")[0]), 
            mo = Integer.parseInt(vals.get(5).split("-")[1]), 
            day = Integer.parseInt(vals.get(5).split("-")[2]);

        this.username = vals.get(1);
        this.passwordHash = vals.get(2);
        this.fname = vals.get(3);
        this.lname = vals.get(4);
        this.role = Integer.parseInt( vals.get(6) );
        this.date_of_birth = Calendar.getInstance();
        this.date_of_birth.set( yr, mo, day );
        this.id = Integer.parseInt( vals.get(0) );
    }

    /**
     * Just fetch username, pw and role from the DB for login check.
     * @param username
     * @param hashedPass
     * @param role
     */
    public User(String username, String hashedPass, String role){
        this.username = username;
        this.passwordHash = hashedPass;
        this.role = Integer.parseInt(role);
    }

    //getters
    public String getUsername()
    {
        return this.username;
    }

    public String getPasswordHash()
    {
        return this.passwordHash;
    }

    public String getFName()
    {
        return this.fname;
    }

    public String getLName()
    {
        return this.lname;
    }

    public int getRole()
    {
        return this.role;
    }

    public Calendar getDOB()
    {
        return this.date_of_birth;
    }

    public int getId()
    {
        return this.id;
    }

    //setters
    public void setUsername( String user )
    {
        this.username = user;
    }

    public void setPasswordHash( String pass )
    {
        this.passwordHash = pass;
    }

    public void setFName( String fname )
    {
        this.fname = fname;
    }

    public void setLName( String lname )
    {
        this.lname = lname;
    }

    public void setRole( int role )
    {
        this.role = role;
    }

    public void setDOB( Calendar date )
    {
        this.date_of_birth = date;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    /**
     * Method used to return values of User object as ArrayList for use as parameters for queries
     * @param id include id or not
     * @return  returns the User object constructed as parameter
     */
    public ArrayList<String> getUserParameters(boolean id){
        ArrayList<String> temp = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        if(id) temp.add(this.id+"");
        temp.add(username);
        temp.add(passwordHash);
        temp.add(fname);
        temp.add(lname);
        temp.add( format.format(date_of_birth) );
        temp.add(role+"");

        return temp;
    }
}