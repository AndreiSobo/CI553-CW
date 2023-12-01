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
    // Class.forName("com.mysql.jdbc.Driver");
    
  }

  public String urlOfDatabase()
  {
    // Return URL of your MySQL database
    // Replace hostname, port, and dbname with your values

    return "jdbc:mysql://178.128.37.54:3306/as2491_catshop2";
  }

  public String username()
  {
    return "as2491_user_test";
  }

  public String password()
  {
    return "Deoareceichtyandro123";
  }
}
