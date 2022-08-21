package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getDBConnection();
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE users ("
                + " id INTEGER NOT NULL AUTO_INCREMENT,"
                + " name VARCHAR(45),"
                + " lastname VARCHAR(45),"
                + " age INTEGER,"
                + " PRIMARY KEY (id)"
                + ");";
        try (Statement statement = connection.createStatement()) {
//            connection.setAutoCommit(true); // по-умолчанию
            statement.executeUpdate(sql);
//            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE users;";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = String.format("INSERT INTO users (username, lastname, age)"
                + " values ('%s', '%s', '%d');", name, lastName, (int) age);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.printf("User с именем – %s добавлен в базу данных%n", name);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
        }
    }

    public void removeUserById(long id) {
        String sql = String.format("DELETE FROM users WHERE id=%d;", id);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users;";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User(
                        rs.getString("username"),
                        rs.getString("lastname"),
                        rs.getByte("age"));
                user.setId(rs.getLong("id"));
                list.add(user);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
        }
        return list;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
        }
    }
}
