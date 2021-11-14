package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String createSchema = "CREATE SCHEMA IF NOT EXISTS jmpp1;";
    private static final String useSchema = "USE jmpp1;";
    private static final String dropTable = "DROP TABLE IF EXISTS users;";
    private static final String createTable = "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(100), " +
            "lastName VARCHAR(100), " +
            "age TINYINT);";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement(createSchema).execute();
            connection.prepareStatement(useSchema).execute();
            connection.prepareStatement(createTable).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement(useSchema).execute();
            connection.prepareStatement(dropTable).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

    }

    public void removeUserById(long id) {

    }

    public List<User> getAllUsers() {
        return null;
    }

    public void cleanUsersTable() {

    }
}
