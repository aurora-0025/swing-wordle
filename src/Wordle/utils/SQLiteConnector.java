package Wordle.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteConnector {
    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    public SQLiteConnector() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:userdata.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30); // set timeout to 30 sec.
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet queryDb(String q) {
        try {
            rs = statement.executeQuery(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    // public static void main(String[] args) {
    //     Connection connection = null;
    //     try {
    //         // create a database connection
    //         connection = DriverManager.getConnection("jdbc:sqlite:userdata.db");
    //         Statement statement = connection.createStatement();
    //         statement.setQueryTimeout(30); // set timeout to 30 sec.

    //         statement.executeUpdate("drop table if exists words");
    //         statement.executeUpdate("create table words (id integer primary key autoincrement, name string)");
    //         try {
    //             Scanner scanner = new Scanner(new File("src\\Wordle\\utils\\words.txt"));
    //             while (scanner.hasNextLine()) {
    //                 String line = scanner.nextLine();
    //                 System.out.println(line);
    //                 statement.executeUpdate("insert into words (name) values ('"+line+"')");
    //             }
    //             scanner.close();
    //         } catch (FileNotFoundException e) {
    //             e.printStackTrace();
    //         }
    //         ResultSet rs = statement.executeQuery("select * from words");
    //         while (rs.next()) {
    //             // read the result set
    //             System.out.println("name = " + rs.getString("name"));
    //             System.out.println("id = " + rs.getInt("id"));
    //         }
    //     } catch (SQLException e) {
    //         // if the error message is "out of memory",
    //         // it probably means no database file is found
    //         System.err.println(e.getMessage());
    //     } finally {
    //         try {
    //             if (connection != null)
    //                 connection.close();
    //         } catch (SQLException e) {
    //             // connection close failed.
    //             System.err.println(e.getMessage());
    //         }
    //     }
    // }
}