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
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = Util.getDBConnection();
        String sql = "CREATE TABLE users ("
                + " id INTEGER NOT NULL AUTO_INCREMENT,"
                + " username VARCHAR(45),"
                + " lastname VARCHAR(45),"
                + " age INTEGER,"
                + " PRIMARY KEY (id)"
                + ");";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
//            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        Connection connection = Util.getDBConnection();
        String sql = "DROP TABLE users;";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
//            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getDBConnection();
        String sql = String.format("INSERT INTO users (username, lastname, age)"
                        + " values ('%s', '%s', '%d');",
                name, lastName, (int) age);
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.printf("User с именем – %s добавлен в базу данных%n", name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        Connection connection = Util.getDBConnection();
        String sql = String.format("DELETE FROM users WHERE id=%d;", id);
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
//            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Connection connection = Util.getDBConnection();
        String sql = "SELECT * FROM users;";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                User user = new User(
                        rs.getString("username"),
                        rs.getString("lastname"),
                        rs.getByte("age"));
                user.setId(rs.getLong("id"));
                list.add(user);
            }
        } catch (SQLException e) {
//            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        Connection connection = Util.getDBConnection();
        String sql = "DELETE FROM users";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
//            throw new RuntimeException(e);
        }
    }
}
