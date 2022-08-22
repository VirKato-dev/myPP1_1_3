package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        // Hibernate
        UserService tableUsers = new UserServiceImpl();
        tableUsers.createUsersTable();
        tableUsers.saveUser("Вася", "Егоров", (byte) 20);
        tableUsers.saveUser("Миша", "Иванов", (byte) 18);
        tableUsers.saveUser("Анна", "Сёмина", (byte) 27);
        tableUsers.saveUser("Галя", "Пикова", (byte) 23);
        tableUsers.getAllUsers().forEach(System.out::println);
        tableUsers.cleanUsersTable();
        tableUsers.dropUsersTable();
    }
}
