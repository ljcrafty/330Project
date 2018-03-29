import java.util.*;
import java.sql.*;
import java.io.*;

public class DLException extends Exception
{
    private Exception e;
    private HashMap<String, String> info;

    /**
     * Constructor
     * @param e -   the Exception object with pertinent data for this DLException
     */
    public DLException(Exception e)
    {
        this(e, new HashMap<String, String>());
    }

    /**
     * Constructor
     * @param e     -   the Exception object with pertinent data for this DLException
     * @param info  -   a HashMap of String, String pairs representing additional information 
     *                  about the exception
     */
    public DLException(Exception e, HashMap<String, String> info)
    {
        this.e = e;
        this.info = info;
        this.info.put( "Message: ", e.getMessage());

        Throwable cause = this.e.getCause();
        String tempStr = "";

        while( cause != null )
        {
            tempStr += "- " + cause.getMessage() + "\n";
            cause = cause.getCause();
        }

        if( tempStr.length() > 5)
            this.info.put( "Cause: ", tempStr);

        switch( e.getClass().toString() )
        {
            case "class java.sql.SQLException":
            case "class java.sql.SQLNonTransientException":
            case "class java.sql.SQLTransientException":
            case "class java.sql.SQLRecoverableException":
            case "class javax.sql.rowset.serial.SerialException":
            case "class javax.sql.rowset.RowSetWarning":
            case "class javax.sql.rowset.spi.SyncFactoryException":
            case "class javax.sql.rowset.spi.SyncProviderException":
                SQLException ex = (SQLException)e;
                this.info.put( "Error Code: ", Integer.toString(ex.getErrorCode()) );
                this.info.put( "SQL State: ", ex.getSQLState() );
                break;

            case "class java.sql.BatchUpdateException":
                BatchUpdateException exe = (BatchUpdateException)e;
                this.info.put( "Error Code: ", Integer.toString(exe.getErrorCode()) );
                this.info.put( "SQL State: ", exe.getSQLState() );
                this.info.put( "Update Counts: ", Arrays.toString(exe.getUpdateCounts()) );
                break;

            case "class java.sql.SQLClientInfoException":
                SQLClientInfoException exep = (SQLClientInfoException)e;
                Map<String, ClientInfoStatus> map = exep.getFailedProperties();
                String temp = "";

                this.info.put( "Error Code: ", Integer.toString(exep.getErrorCode()) );
                this.info.put( "SQL State: ", exep.getSQLState() );

                for( Map.Entry<String, ClientInfoStatus> entry : map.entrySet() )
                {
                    temp += "- " + entry.getKey() + ": " + entry.getValue() + "\n";
                }

                this.info.put( "Failed Client Info Properties: ",  temp);
                break;

            default:
                break;
        }

        log();
    }

    /**
     * Creates a log entry in the log file with the information stored in this object
     */
    public void log()
    {
        try
        {
            FileOutputStream fos = new FileOutputStream( new File("log.txt"), true );
            PrintWriter pw = new PrintWriter(fos);

            pw.append( new java.util.Date().toString() + '\n' );
            pw.append( this.e.getClass().toString() + '\n' );
            
            for( String key : this.info.keySet() )
            {
                pw.append( key + ": " + this.info.get(key) + '\n' );
            }
            
            pw.print( '\n' );

            pw.close();
        }
        catch( IOException e )
        {
            return;
        }
    }
}