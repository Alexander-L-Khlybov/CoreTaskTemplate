package jm.task.core.jdbc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String PROP_PATH =
            "src/main/java/jm/task/core/jdbc/resources/config.properties";

    public static Connection getConnectionJDBC() throws SQLException {
        Properties properties = new Properties();

        try (FileInputStream fIS = new FileInputStream(PROP_PATH)) {
            properties.load(fIS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(
                properties.getProperty("dbURL"),
                properties.getProperty("dbUser"),
                properties.getProperty("dbPassword")
        );
    }
}
