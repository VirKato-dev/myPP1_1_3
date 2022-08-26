package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;


public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        // Hibernate
        //TODO Использует ли Hibernate внутри себя JDBC?
        //TODO Что такое JPA? Чем JPA отличается от Hibernate?
        UserService tableUsers = new UserServiceImpl();
        tableUsers.createUsersTable();

        tableUsers.saveUser("Вася", "Егоров", (byte) 20);
        tableUsers.saveUser("Миша", "Иванов", (byte) 18);
        tableUsers.saveUser("Анна", "Сёмина", (byte) 27);
        tableUsers.saveUser("Галя", "Пикова", (byte) 23);

        tableUsers.removeUserById(3);

        tableUsers.getAllUsers().forEach(System.out::println);

//        tableUsers.cleanUsersTable();
//        tableUsers.dropUsersTable();
        Util.getSessionFactory().close();
    }
}
