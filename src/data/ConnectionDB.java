
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionDB {
    
    public static Connection conecction;
    
        
    /**
     * Stablish the connection with the database
     * @return [Connection]: Object that allow manipulate the database
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public Connection openConnection() throws ClassNotFoundException, SQLException{
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/resourcesdb";
        Class.forName(driver);
        conecction = DriverManager.getConnection(url, "root", "");
        
        return conecction;
    }
    
    /**
     * Close the database connection
     * @throws SQLException 
     */
    public void closeConnection() throws SQLException{
        conecction.close();
    }
    
}
