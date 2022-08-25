package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        //TODO Зачем здесь конструктор по-умолчанию? (перезаписываемые методы не могут быть static)
        sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS USERS (" + //SQL
                            " id INT NOT NULL AUTO_INCREMENT," +
                            " name VARCHAR(45) NOT NULL ," +
                            " lastname VARCHAR(45) NOT NULL ," +
                            " age INT," +
                            " PRIMARY KEY (id))")
                    .executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users;";
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.printf("User с именем – %s добавлен в базу данных%n", name);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        User user = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            //TODO почему удаление по сущности более правильно, чем удаление запросом по айди.
            user = session.get(User.class, id); // можно переиспользовать объект
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        System.out.println("Удалён пользователь: " + (user != null ? user : "(нет объекта)"));
    }

    //TODO JPQL HQL SQL
    @Override
    public List<User> getAllUsers() {
        List<User> userList;
        try (Session session = sessionFactory.openSession()) {
            userList = session.createQuery("FROM User", User.class).getResultList(); //HQL
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate(); //SQL
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
