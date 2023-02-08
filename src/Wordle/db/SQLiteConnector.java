package Wordle.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Wordle.model.User;
import at.favre.lib.crypto.bcrypt.BCrypt;

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

    public String getRandomWord() {
        try {
            ResultSet rs = statement.executeQuery("select name from words order by random() limit 1;");
            if(rs.next()) {
                return rs.getString("name");
            }
            else {
                return "No Word Found";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    public boolean checkIfWordExists(String word) {
        try {
            ResultSet rs = statement.executeQuery("select name from allWords where name='" + word + "' limit 1;");
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getUser(String username, String password) throws UserError {
        try {
            ResultSet rs = statement.executeQuery("select * from userdata where username ='" + username + "' limit 1;");
            if(rs.next()) {
                String pwdHash = rs.getString("passwordHash");
                BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), pwdHash);
                if(result.verified) {
                    return new User(username, pwdHash, rs.getInt("wins"), rs.getInt("losses"), rs.getInt("currentStreak"), rs.getInt("maxStreak"));
                }
                else throw new UserError(2);
            } 
            else throw new UserError(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class UserError extends Throwable {
        public static final int USER_NOT_FOUND = 1;
        public static final int INCORRECT_PASSWORD = 2;
        private int errorCode;
        UserError(int errCode) {
            this.errorCode = errCode;
        }
        public int getErrorCode() {
            return this.errorCode;
        }
    }
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