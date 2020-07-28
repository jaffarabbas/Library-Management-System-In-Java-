package Libaray;

import Libaray.DbConnection.MainConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConn{

    public Connection connection;
    public Statement statement;
    public ResultSet result;



    private static final String DATABASE_URL = "jdbc:mysql://localhost/Library_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";
    private static final String SELECT_QUERY_LOGIN = "SELECT * FROM login WHERE name = ? and password = ?";
    private static final String INSERT_QUERY_BOOK = "INSERT INTO `book_collection` (`id`, `sno`, `name`, `isbn`, `auther`, `insertion_date`) VALUES (NULL,?,?, ?, ?, ?)";



    public static String UserId;

    public DbConn() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
              e.printStackTrace();
        }
    }

    public boolean validate(String user, String password) throws SQLException {

        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try (// Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_LOGIN)) {
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                UserId = user;
                return true;
            }

        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        return false;
    }


    //Books inserteion Metthod

    public int insert_Books_query_Executer(String sno, String name, String isbn, String author, LocalDate date) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_BOOK) ;
            preparedStatement.setString(1, sno);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, isbn);
            preparedStatement.setString(4, author);
            preparedStatement.setString(5, String.valueOf(date));
    
            System.out.println(preparedStatement);
              int resultSet =  preparedStatement.executeUpdate();
              return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ResultSet execQuery(String query){
                try{
                    statement = connection.createStatement();
                    result = statement.executeQuery(query);
                }
                catch(SQLException e){
                    printSQLException(e);
                    return null;
                }
                finally {
                }
                return result;
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
