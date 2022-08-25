package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.convert.spi.JpaAttributeConverterCreationContext;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
        }
        return conn;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Properties prop = new Properties();
            prop.put(Environment.DRIVER, driverName);
            prop.put(Environment.URL, "jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false&serverTimezone=UTC");
            prop.put(Environment.USER, userName);
            prop.put(Environment.PASS, password);
            prop.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            prop.put(Environment.SHOW_SQL, "false");
            prop.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            prop.put(Environment.HBM2DDL_AUTO, "create-drop"); //TODO какие есть параметры аргумента ddl-auto

            Configuration configuration = new Configuration()
                    .setProperties(prop)
                    .addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }
}
