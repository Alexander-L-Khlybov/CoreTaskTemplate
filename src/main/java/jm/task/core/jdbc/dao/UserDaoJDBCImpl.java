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

    private static final String createSchema = "CREATE SCHEMA IF NOT EXISTS jmpp1;";
    private static final String useSchema = "USE jmpp1;";
    private static final String dropTable = "DROP TABLE IF EXISTS users;";
    private static final String createTable = "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(100), " +
            "lastName VARCHAR(100), " +
            "age TINYINT);";
    private static final String insertData =
            "INSERT INTO jmpp1.users (name, lastName, age) VALUES (?, ?, ?)";
    private static final String removeData =
            "DELETE FROM jmpp1.users " +
                    "WHERE jmpp1.users.id = ?;";
    private static final String usList = "SELECT * FROM jmpp1.users;";

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
        PreparedStatement ps;
        try (Connection connection = Util.getConnection()) {
            ps = connection.prepareStatement(insertData);
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
        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement(useSchema);
            ps = connection.prepareStatement(removeData);
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new LinkedList<>();
        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement(useSchema);
            ResultSet rs = connection.prepareStatement(usList).executeQuery();

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
        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement(useSchema);
            connection.prepareStatement("DELETE FROM jmpp1.users;").execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
