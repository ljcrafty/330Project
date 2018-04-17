package Controllers;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

//takes care of pushing changes that occur in the library to the database
public class MySQLDatabase
{
    private HashMap<String, String> data;
    private Connection conn;
    
    public MySQLDatabase(String dbType, String port, String ip, String db, 
        String user, String pass)
    {
        this.data = new HashMap<String, String>();
        this.data.put("dbType", dbType);
        this.data.put("port", port);
        this.data.put("ip", ip);
        this.data.put("db", db);
        this.data.put("user", user);
        this.data.put("pass", pass);
    }
    
    /**
        Attempt to connect to database with given properties
    */
    public void connect() throws DLException
    {
        this.connect(0);
    }

    /**
        Attempt to connect to database with given properties
        @param num  -   The number of times the function has been called recently 
                        (for recursive purposes)
    */
    private void connect(int num) throws DLException
    {
        try
        {
            String url = "jdbc:" + this.data.get("dbType") + "://" +
                this.data.get("ip") + ":" + this.data.get("port") + "/" +
                this.data.get("db");
            
            this.conn = DriverManager.getConnection(url, this.data.get("user"),
                this.data.get("pass"));
        }
        //can be retried
        catch(SQLTransientException e)
        {
            //retry the action
            if( num < 5 )
            {
                try
                {
                    this.connect( num + 1 );
                    DLException ex = new DLException(e);//log without throwing on success
                }
                catch(DLException ex)
                {
                    throw new DLException(e);
                }
            }
            //give up
            else
            {
                throw new DLException(e);
            }
        }
        //cannot be retried
        catch(Exception e)
        {
            throw new DLException(e);
        }
    }
    
    /**
        Attempt to close database with given properties
        @return whether or not the closing was successful
    */
    public boolean close() throws DLException
    {
        return this.close(0);
    }

    /**
        Attempt to close database with given properties
        @param num  -   The number of times the function has been called recently 
                        (for recursive purposes)
        @return whether or not the closing was successful
    */
    private boolean close(int num)  throws DLException
    {
        if( this.conn == null )
            return true;

        try
        {
            this.conn.close();
            return true;
        }
        catch(SQLTransientException e)
        {
            //retry the action
            if( num < 5 )
            {
                try
                {
                    this.close( num + 1 );
                    DLException ex = new DLException(e);//log without throwing on success
                }
                catch(DLException ex)
                {
                    throw new DLException(e);
                }
            }
            //give up
            else
            {
                throw new DLException(e);
            }
            return false;
        }
        //cannot be retried
        catch(Exception e)
        {
            throw new DLException(e);
        }
    }
    
    /** 
        Set a property of the database
        @param key -  the property you'd like to set; options are: 'dbType', 'port', 
                        'ip', 'db', 'user', and 'pass'
        @param val -  the value to set the property to
    */
    public void set(String key, String val)
    {
        if( this.data.containsKey(key) )
        {
            this.data.replace(key, val);
        }
    }
    
    /**
        Get a property of the database
        @param key -  the property you'd like to get; options are: 'dbType', 'port', 
                        'ip', 'db', 'user', and 'pass'
        @param returns - the value of the property
    */
    public String get(String key)
    {
        if( this.data.containsKey(key) )
        {
            return this.data.get(key);
        }
        
        return "";
    }

    /**
        Gets data using a prepared statement
        @param query    -   the query to execute on the database
        @param params   -   the parameters to bind to the query being executed
        @return  an ArrayList of ArrayLists of Strings making up the rows that were
                    returned from the database and their column names if specified, 
                    or null if there was an error.
    */
    public ArrayList<ArrayList<String>> getData( String query, ArrayList<String> params )
    {
        //check that the query is the right type of statement
        String verb = query.split(" ")[0].toLowerCase();

        if( (!verb.equals("select") || this.conn == null ) )
        {
            return null;
        }

        try
        {
            PreparedStatement stmt = this.prepare( query, params );
            ResultSet rs = stmt.executeQuery();

            return this.parseResultSet(rs, false);
        }
        catch( Exception e )
        {
            return null;
        }
    }

    /**
        Changes data in the database by using a prepared statement with the 'insert', 'update', or 'delete' verbs
        @param query   -   the insert, update, or delete query to be prepared and run
        @param params  -   the parameters to bind to the query being executed
        @return a boolean representing whether or not the query was successfully run
    */
    public boolean setData( String query, ArrayList<String> params )
    {
        //check that the query is the right type of statement
        String verb = query.split(" ")[0].toLowerCase();

        if( (!verb.equals("update") && !verb.equals("insert") && !verb.equals("delete")) || this.conn == null )
        {
            return false;
        }

        try
        {
            PreparedStatement stmt = this.prepare(query, params);
            int numRows = stmt.executeUpdate();

            if( numRows > 0 )
            {
                return true;
            }
            return false;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    /**
        Executes a stored procedure on the database
        @param query    -   the stored procedure to run
        @param params   -   the parameters needed to run the query
        @return the number of affected rows after running the procedure
    */
    public int execProc( String query, ArrayList<String> params ) throws DLException
    {
        try
        {
            CallableStatement stmt = this.conn.prepareCall(query);

            for( int i = 0; i < params.size(); i++ )
            {
                stmt.setString(i + 1, params.get(i));
            }

            return stmt.executeUpdate();
        }
        catch( Exception e )
        {
            throw new DLException(e);
        }
    }

    /**
        Prepare a statement to be executed on this database
        @param query    -   the query to prepare
        @param params   -   an ArrayList of Strings giving the parameters to bind to the statement
        @return the statement that was prepared with the given paramters
    */
    public PreparedStatement prepare( String query, ArrayList<String> params ) throws DLException
    {
        try
        {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            
            for( int i = 0; i < params.size(); i++ )
            {
                stmt.setString(i + 1, params.get(i) );
            }

            return stmt;
        }
        catch( Exception e )
        {
            throw new DLException(e);
        }
    }

    /**
        Helper function to make an ArrayList of ArrayLists of Strings out of a ResultSet
        @param rs       -   the ResultSet to process
        @param colNames -   whether or not to include column names in the output
        @return an ArrayList of ArrayLists of Strings from the given data
    */
    private ArrayList<ArrayList<String>> parseResultSet( ResultSet rs, boolean colNames ) throws SQLException
    {
        ArrayList<String> temp = new ArrayList<String>();
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

        try
        {
            //add column names
            if( colNames )
            {
                for( int i = 1; i <= rs.getMetaData().getColumnCount(); i++ )
                {
                    temp.add( rs.getMetaData().getColumnName(i) );
                }
                data.add( temp );
            }
                
            while( rs.next() )
            {
                temp = new ArrayList<String>();

                for( int i = 1; i <= rs.getMetaData().getColumnCount(); i++ )
                {
                    temp.add( rs.getString(i) );
                }

                data.add( temp );
            }
            
            return data;
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    /**
        Starts a transaction in the database
     */
    public void startTrans() throws DLException
    {
        try
        {
            this.conn.setAutoCommit( false );
        }
        catch( SQLException e )
        {
            throw new DLException(e);
        }
    }

    /**
        Finishes a transaction in the database by committing changes and enabling auto-commit
     */
    public void endTrans() throws DLException
    {
        try
        {    
            this.conn.commit();
            this.conn.setAutoCommit( true );
        }
        catch( SQLException e )
        {
            throw new DLException(e);
        }
    }

    /**
        Rolls back changes made during the last transaction and enables auto-commit
     */
    public void rollbackTrans() throws DLException
    {
        try
        {    
            this.conn.rollback();
            this.conn.setAutoCommit( true );
        }
        catch( SQLException e )
        {
            throw new DLException(e);
        }
    }
}//end class