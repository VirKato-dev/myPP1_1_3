package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String hostName = "localhost";
    private static final String dbName = "my_db";
    private static final String userName = "root";
    private static final String password = "root";
    private static final String driverName = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;
    private static SessionFactory sessionFactory = null;

    private Util() {
    }

    public static Connection getDBConnection() {
        return getDBConnection(hostName, dbName, userName, password);
    }

    public static Connection getDBConnection(String hostName, String dbName, String userName, String password) {
        if (conn != null) return conn;
        try {
            Class.forName(driverName);
            String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
            conn = DriverManager.getConnection(connectionURL, userName, password);
//            System.out.println(conn.getMetaData().getDriverName());
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return conn;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .addAnnotatedClass(User.class) // Hibernate теперь знает о классе User
                    .setProperty("hibernate.connection.driver_class", driverName)
                    .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false&serverTimezone=UTC")
                    .setProperty("hibernate.connection.username", userName)
                    .setProperty("hibernate.connection.password", password)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .setProperty("hibernate.show_sql", "false") // true для просмотре в консоли SQL запроса
                    .setProperty("hibernate.current_session_context_class", "thread")
                    .setProperty("hbm2ddl_auto", "create-drop")
                    .buildSessionFactory();
        }
        return sessionFactory;
    }
}
