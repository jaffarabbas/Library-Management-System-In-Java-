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
    public static DbConn handler = null;



    private static final String DATABASE_URL = "jdbc:mysql://localhost/Library_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "";
    private static final String SELECT_QUERY_LOGIN = "SELECT * FROM login WHERE name = ? and password = ?";
    private static final String INSERT_QUERY_BOOK = "INSERT INTO `book_collection` (`id`, `sno`, `name`, `isbn`, `auther`) VALUES (NULL,?,?, ?, ?)";
    private static final String INSERT_QUERY_MEMBER = "INSERT INTO `member_collection` (`name`, `number`, `address`, `card_number`) VALUES (?, ?, ?,?)";
    private static final String DELETE_BOOKS = "DELETE FROM `book_collection` WHERE sno = ?";
    private static final String IS_BOOK_IS_ALREADY_ISSUED_BOOKS = "SELECT COUNT(*) FROM issued_books WHERE bookId = ?";
    private static final String UPDATE_BOOK = "UPDATE `book_collection` SET name = ? ,isbn = ? , auther = ? WHERE sno = ?";
    private static final String DELETE_MEMBER = "DELETE FROM `member_collection` WHERE card_number = ?";
    private static final String IS_MEMBER_IS_ALREADY_HAVE_BOOKS = "SELECT COUNT(*) FROM issued_books WHERE memberId = ?";
    private static final String UPDATE_MEMBER = "UPDATE `member_collection` SET name = ? ,number = ? , address = ? WHERE card_number = ?";


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

    //Books inserteion Metthod

    public int insert_Books_query_Executer(String sno, String name, String isbn, String author) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_BOOK) ;
            preparedStatement.setString(1, sno);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, isbn);
            preparedStatement.setString(4, author);
    
            System.out.println(preparedStatement);
              int resultSet =  preparedStatement.executeUpdate();
              return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Member insertion
    public int insert_Member_query_Executer(String name, String number, String address, String cardNumber) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY_MEMBER) ;
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, number);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, cardNumber);

            System.out.println(preparedStatement);
            int resultSet =  preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //book deletion
    public boolean DeleteBooks(BooksCollection.Book books){
        try {
             PreparedStatement statement = connection.prepareStatement(DELETE_BOOKS);
             statement.setString(1,books.getSno());
             int result = statement.executeUpdate();
             if(result ==  1){
                 return true;
             }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //checking issued book that it already exist
    public boolean IsBookAlreadyIssued(BooksCollection.Book books){
        try {
            PreparedStatement statement = connection.prepareStatement(IS_BOOK_IS_ALREADY_ISSUED_BOOKS);
            statement.setString(1,books.getSno());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                System.out.println("Already issue : "+count);
                return count > 0;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //Update Book
    public boolean updateBook(BooksCollection.Book book){
       try{
           PreparedStatement statement = connection.prepareStatement(UPDATE_BOOK);
           statement.setString(1, book.getName());
           statement.setString(2, book.getAuthor());
           statement.setString(3, book.getIsbn());
           statement.setString(4,book.getSno());
           int result = statement.executeUpdate();
           return (result>0);
       }catch (SQLException e){
           e.printStackTrace();
       }
       return false;
    }

    //Members crud

    //book deletion
    public boolean DeleteMembers(Member_list.Members members){
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_MEMBER);
            statement.setString(1,members.getCard_number());
            int result = statement.executeUpdate();
            if(result ==  1){
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //checking issued book that it already exist
    public boolean IsMemberIsAlreadyIssued(Member_list.Members members){
        try {
            PreparedStatement statement = connection.prepareStatement(IS_MEMBER_IS_ALREADY_HAVE_BOOKS);
            statement.setString(1, members.getCard_number());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                int count = resultSet.getInt(1);
                System.out.println("Already issue : "+count);
                return count > 0;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //Update Book
    public boolean updateMember(Member_list.Members members){
        try{
            PreparedStatement statement = connection.prepareStatement(UPDATE_MEMBER);
            statement.setString(1, members.getName());
            statement.setString(2, members.getNumber());
            statement.setString(3, members.getAddress());
            statement.setString(4, members.getCard_number());
            int result = statement.executeUpdate();
            return (result>0);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
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
        finally { }
          return result;
    }

    public boolean execAction(String query){
        ResultSet resultSet;
        try{
            statement = connection.createStatement();
            statement.execute(query);
            return true;
        }catch (SQLException exception){
            printSQLException(exception);
            System.out.println("Error Message Of DataBase Connection Class : "+exception.getLocalizedMessage());
            return false;
        }finally {
        }
    }

    //get Instance

    public static DbConn getInstance(){
        if(handler == null){
            handler = new DbConn();
        }
        return handler;
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
