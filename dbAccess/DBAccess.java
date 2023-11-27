package dbAccess;

/**
  * Implements generic management of a database.
  * @author  Mike Smith University of Brighton
  * @version 2.0
  */
 
/**
 * Base class that defines the access to the database driver
 */
public class DBAccess
{
  public void loadDriver() throws Exception
  {
    // Load MySQL JDBC driver
    Class.forName("com.mysql.cj.jdbc.Driver");
  }

  public String urlOfDatabase()
  {
    // Return URL of your MySQL database
    // Replace hostname, port, and dbname with your values
    return "jdbc:mysql://hostname:port/as2491_catshop2";
  }

  public String username()
  {
    return "as2491";
  }

  public String password()
  {
    return "Trabant123Cacat123";
  }
}
