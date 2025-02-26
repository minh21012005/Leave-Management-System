package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DBcontext {

    protected Connection connection;

    public DBcontext() {
        try {
            String user = "sa";
            String pass = "21012005";
            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=CompanyDB;encrypt=true;trustServerCertificate=true;";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBcontext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
