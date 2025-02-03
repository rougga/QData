package ma.rougga.qdata;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteConnection {

    private final String DRIVER = "jdbc:sqlite:database.db";
   // private final String CONNECTIONSTRING = "jdbc:postgresql://localhost:5434/postgres";
//    private final String CONNECTIONSTRING = "jdbc:postgresql://localhost:5432/postgres";
//    private final String USER = "honyi";
//    private final String PASSWORD = "honyi123";
    //  private final String CONNECTIONSTRING = "jdbc:postgresql://localhost:5555/postgres";
    //  private final String USER = "postgres";
    // private final String PASSWORD = "123456";
    private Connection con = null;
    private Statement st = null;

    public SQLiteConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection(DRIVER);
    }

    public Statement getStatement() throws SQLException {
        st = con.createStatement();
        st.setQueryTimeout(30);
        return st;
    }

    public void closeConnection() throws SQLException {
        st.close();
        con.close();
    }
}
