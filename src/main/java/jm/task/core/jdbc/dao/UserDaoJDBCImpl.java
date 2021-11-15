package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE_SCHEMA =
            "CREATE SCHEMA IF NOT EXISTS jmpp1;";
    private static final String USE_SCHEMA =
            "USE jmpp1;";
    private static final String DROP_TABLE =
            "DROP TABLE IF EXISTS users;";
    private static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100), " +
                "lastName VARCHAR(100), " +
                "age TINYINT);";
    private static final String INSERT_DATA =
            "INSERT INTO jmpp1.users (name, lastName, age) VALUES (?, ?, ?)";
    private static final String REMOVE_DATA =
            "DELETE FROM jmpp1.users WHERE jmpp1.users.id = ?;";
    private static final String USERS_LIST =
            "SELECT * FROM jmpp1.users;";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnectionJDBC()) {
            connection.prepareStatement(CREATE_SCHEMA).execute();
            connection.prepareStatement(USE_SCHEMA).execute();
            connection.prepareStatement(CREATE_TABLE).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnectionJDBC()) {
            connection.prepareStatement(USE_SCHEMA).execute();
            connection.prepareStatement(DROP_TABLE).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement ps;
        try (Connection connection = Util.getConnectionJDBC()) {
            connection.prepareStatement(USE_SCHEMA).execute();
            ps = connection.prepareStatement(INSERT_DATA);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        PreparedStatement ps;
        try (Connection connection = Util.getConnectionJDBC()) {
            connection.prepareStatement(USE_SCHEMA).execute();
            ps = connection.prepareStatement(REMOVE_DATA);
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new LinkedList<>();
        try (Connection connection = Util.getConnectionJDBC()) {
            connection.prepareStatement(USE_SCHEMA).execute();
            ResultSet rs = connection.prepareStatement(USERS_LIST).executeQuery();

            while (rs.next()) {
                usersList.add(new User(
                        rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getByte("age")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnectionJDBC()) {
            connection.prepareStatement(USE_SCHEMA).execute();
            connection.prepareStatement("DELETE FROM jmpp1.users;").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
