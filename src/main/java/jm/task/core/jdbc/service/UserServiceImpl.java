package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private User testUser;
    private final List<User> testUsers = new ArrayList<>();

    public void createUsersTable() {

    }

    public void dropUsersTable() {

    }

    public void saveUser(String name, String lastName, byte age) {
        testUser = new User(name, lastName, age);
        testUser.setId(0L);
        testUsers.add(testUser);
    }

    public void removeUserById(long id) {

    }

    public List<User> getAllUsers() {
        return testUsers;
    }

    public void cleanUsersTable() {

    }
}
