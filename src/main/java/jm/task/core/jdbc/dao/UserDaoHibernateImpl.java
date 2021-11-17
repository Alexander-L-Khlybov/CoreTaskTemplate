package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.function.Consumer;

public class UserDaoHibernateImpl implements UserDao {

    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS jmpp1.users (" +
            "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(100), " +
            "lastName VARCHAR(100), " +
            "age TINYINT);";
    private static final String DROP_TABLE =
            "DROP TABLE IF EXISTS jmpp1.users;";
    private static final String CLEAN_TABLE =
            "DELETE FROM jmpp1.users;";
    private static final String GET_ALL_USERS =
            "SELECT id, name, lastName, age FROM jmpp1.users;";


    private void simpleQuery(Consumer<Session> s) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactoryHibernate().openSession()) {
            transaction = session.beginTransaction();
            s.accept(session);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }


    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        simpleQuery(session -> session
                .createSQLQuery(CREATE_TABLE).executeUpdate());
    }

    @Override
    public void dropUsersTable() {
        simpleQuery(session -> session
                .createSQLQuery(DROP_TABLE).executeUpdate());
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        simpleQuery(session -> session
                .save(new User(name, lastName, age)));
    }

    @Override
    public void removeUserById(long id) {

        simpleQuery(session -> session
                .delete(session.get(User.class, id)));
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;

        try (Session session = Util.getSessionFactoryHibernate().openSession()) {
            users = (List<User>) session.createSQLQuery(GET_ALL_USERS).addEntity(User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {

        simpleQuery(session -> session
                .createSQLQuery(CLEAN_TABLE).executeUpdate());
    }
}
