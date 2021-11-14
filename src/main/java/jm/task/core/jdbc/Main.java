package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl udo = new UserDaoJDBCImpl();
        udo.dropUsersTable();
        udo.createUsersTable();
        udo.saveUser("ivan", "ivanov", (byte) 18);
        udo.saveUser("ibsvan", "ivanov", (byte) 28);
        udo.saveUser("ivtrh45an", "ivanov", (byte) 8);
        udo.saveUser("iwgevan", "ivanov", (byte) 118);
        udo.removeUserById(2);
        udo.cleanUsersTable();
    }
}
