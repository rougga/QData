package main;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PgMultiConnection {

    private final String DRIVER = "org.postgresql.Driver";
    private final String CONNECTIONSTRING = "jdbc:postgresql://localhost:5432/postgres";
    private final String USER = "honyi";
    private final String PASSWORD = "honyi123";
    private Connection con=null;
    private Statement st=null;

    public PgMultiConnection(String host,String port,String database,String username,String password) throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        String CONSTR="jdbc:postgresql://"+host+":"+port+"/"+database;
        con=DriverManager.getConnection(CONSTR,username,password);
    }

    public Statement getStatement() throws SQLException {
         st= con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        return st;
    }

    public void closeConnection() throws SQLException {
        st.close();
        con.close();
    }
}
