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

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/library_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";
    private static final String SELECT_QUERY_LOGIN = "SELECT * FROM login WHERE name = ? and password = ?";


    public static String UserId;

    public DbConn() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(DbConn.class.getName()).log(Level.SEVERE,null,e);
        }
    }


    public static void main(String[] args) {
        DbConn obj= new DbConn();
        obj.Connection();



    //login

    public boolean validate(String name, String password) throws SQLException {

        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try (// Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_LOGIN)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                UserId = name;
                return true;
            }

        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        return false;
    }


    //print the error
    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }

    }
}
