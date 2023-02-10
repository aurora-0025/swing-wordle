package Wordle.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Wordle.model.User;

public class SQLiteConnector {
    private static Connection connection = null;
    static Statement statement = null;
    ResultSet rs = null;

    public static Connection getConn() throws SQLException {
        if(connection == null) connection = DriverManager.getConnection("jdbc:sqlite:userdata.db");
        return connection;
}

    public SQLiteConnector() {
        try {
            connection = getConn();
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getRandomWord() {
        try {
            
            ResultSet rs = statement.executeQuery("select name from words order by random() limit 1;");
            if (rs.next()) {
                return rs.getString("name");
            } else {
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
            if (rs.next()) {
                String pwdHash = rs.getString("passwordHash");
                if(pwdHash.equals(password)) {
                    return new User(username, pwdHash, rs.getInt("wins"), rs.getInt("losses"), rs.getInt("currentStreak"), rs.getInt("maxStreak"));
                } else throw new UserError(2);
            } else
                throw new UserError(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateUser(User user) {
        try {
            System.out.println(user.getWins());
            //statement.executeUpdate("insert into userdata (username, passwordHash, wins, losses, currentStreak, maxStreak) values ('"+user.getUsername()+"', '"+user.getPasswordHash()+"', "+user.getWins()+", "+user.getLosses()+", "+user.getCurrentStreak()+", "+user.getMaxStreak()+")");
            statement.executeUpdate("update userdata set username='"+user.getUsername()+"',passwordHash='"+user.getPasswordHash()+"',wins='"+user.getWins()+
            "',losses='"+user.getLosses()+"',currentStreak='"+user.getCurrentStreak()+"',maxStreak='"+user.getMaxStreak()+"' where username='"+user.getUsername()+"'");
            System.out.println("Updated User "+user.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User createUser(String username, String password) {
        try {
            statement.executeUpdate("insert into userdata (username, passwordHash, wins, losses, currentStreak, maxStreak) values ('"+username+"', '"+password+"', 0, 0, 0, 0)");
            return new User(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeConnection() {
        try {
            connection.close();
            System.out.println("Closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
// Connection connection = null;
// try {
// // create a database connection
// connection = DriverManager.getConnection("jdbc:sqlite:userdata.db");
// Statement statement = connection.createStatement();
// statement.setQueryTimeout(30); // set timeout to 30 sec.

// statement.executeUpdate("drop table if exists words");
// statement.executeUpdate("create table words (id integer primary key
// autoincrement, name string)");
// try {
// Scanner scanner = new Scanner(new File("src\\Wordle\\utils\\words.txt"));
// while (scanner.hasNextLine()) {
// String line = scanner.nextLine();
// System.out.println(line);
// statement.executeUpdate("insert into words (name) values ('"+line+"')");
// }
// scanner.close();
// } catch (FileNotFoundException e) {
// e.printStackTrace();
// }
// ResultSet rs = statement.executeQuery("select * from words");
// while (rs.next()) {
// // read the result set
// System.out.println("name = " + rs.getString("name"));
// System.out.println("id = " + rs.getInt("id"));
// }
// } catch (SQLException e) {
// // if the error message is "out of memory",
// // it probably means no database file is found
// System.err.println(e.getMessage());
// } finally {
// try {
// if (connection != null)
// connection.close();
// } catch (SQLException e) {
// // connection close failed.
// System.err.println(e.getMessage());
// }
// }
// }