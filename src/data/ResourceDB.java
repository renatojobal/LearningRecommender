package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ResourceDB {
    
    ConnectionDB conecction = new ConnectionDB();
     
    public ResultSet getResource(String title) throws ClassNotFoundException, SQLException{
        
        Statement statement = conecction.openConnection().createStatement();
        String query = String.format("SELECT * FROM Resource "
                + "WHERE title = '%s'", title);
 
        return statement.executeQuery(query);
    }
}