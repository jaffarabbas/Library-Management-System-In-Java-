package Libaray;

import Libaray.DbConnection.MainConnection;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConn implements MainConnection {

    public Connection connection;
    public Statement statement;
    public ResultSet result;

    @Override
    public void Connection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(DbConn.class.getName()).log(Level.SEVERE,null,e);
        }
    }

    public static void main(String[] args) {
        DbConn obj= new DbConn();
        obj.Connection();
    }
}
