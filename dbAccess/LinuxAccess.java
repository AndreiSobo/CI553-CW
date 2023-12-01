package dbAccess;

/**
  * Implements management of an mySQL database on Linux.
  * @author  Mike Smith University of Brighton
  * @version 2.0
  */
class LinuxAccess extends DBAccess
{
  public void loadDriver() throws Exception
{
  System.out.println("loading linux access driver");
  try {
    Class.forName("com.mysql.cj.jdbc.Driver");
    System.out.println("Driver loaded successfully");
  } catch (Exception e) {
    System.out.println("Failed to load driver");
    throw e;
  }
}

public String urlOfDatabase()
{
  System.out.println("loading linux access database url");
  String url = "jdbc:mysql://178.128.37.54:3306/as2491_catshop2";
  System.out.println("Database URL: " + url);
  return url;
}
}
