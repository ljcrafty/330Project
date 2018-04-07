import java.util.ArrayList;

public class User
{
    private String username, passwordHash, fname, lname;
    private int age, id, role;

    public User()
    {
        this.username = "";
        this.passwordHash = "";
        this.fname = "";
        this.lname = "";
        this.role = 0;
        this.age = 0;
        this.id = 0;
    }

    public User( String username, String pass, String fname, String lname, int role, int age, int id )
    {
        this.username = username;
        this.passwordHash = pass;
        this.fname = fname;
        this.lname = lname;
        this.role = role;
        this.age = age;
        this.id = id;
    }

    public User( ArrayList<String> vals )
    {
        this.username = vals.get(1);
        this.passwordHash = vals.get(2);
        this.fname = vals.get(3);
        this.lname = vals.get(4);
        this.role = Integer.parseInt( vals.get(6) );
        this.age = Integer.parseInt( vals.get(5) );
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

    public int getAge()
    {
        return this.age;
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

    public void setAge( int age )
    {
        this.age = age;
    }

    public void setId( int id )
    {
        this.id = id;
    }
}