package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String hostName = "localhost";
    private static final String dbName = "my_db";
    private static final String userName = "root";
    private static final String password = "root";
    private static Connection conn = null;

    private Util() {
    }

    public static Connection getDBConnection() {
        return getDBConnection(hostName, dbName, userName, password);
    }

    public static Connection getDBConnection(String hostName, String dbName, String userName, String password) {
        if (conn != null) return conn;
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver"); // не требуется для JDBC 4.3 включённой в jdk17
            String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
            conn = DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException /*| ClassNotFoundException*/ e) {
            System.out.println(e.getLocalizedMessage());
        }
        return conn;
    }
}
