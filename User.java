public class User
{
    private String username, passwordHash, fname, lname, role;
    private int age;

    public User()
    {
        this.username = "";
        this.passwordHash = "";
        this.fname = "";
        this.lname = "";
        this.role = "";
        this.age = 0;
    }

    public User( String username, String pass, String fname, String lname, String role, int age )
    {
        this.username = username;
        this.passwordHash = pass;
        this.fname = fname;
        this.lname = lname;
        this.role = role;
        this.age = age;
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

    public String getRole()
    {
        return this.role;
    }

    public int getAge()
    {
        return this.age;
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

    public void setRole( String role )
    {
        this.role = role;
    }

    public void setAge( int age )
    {
        this.age = age;
    }
}