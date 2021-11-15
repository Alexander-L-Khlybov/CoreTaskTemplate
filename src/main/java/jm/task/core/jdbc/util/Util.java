package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String PROP_PATH =
            "src/main/java/jm/task/core/jdbc/resources/config.properties";
    private static SessionFactory sessionFactory = null;


    public static Connection getConnectionJDBC() throws SQLException {
        Properties properties = new Properties();

        try (FileInputStream fIS = new FileInputStream(PROP_PATH)) {
            properties.load(fIS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return DriverManager.getConnection(
                properties.getProperty("hibernate.connection.url"),
                properties.getProperty("hibernate.connection.username"),
                properties.getProperty("hibernate.connection.password")
        );
    }

    public static SessionFactory getSessionFactoryHibernate() {

        if (sessionFactory == null) {
            Configuration configuration = new Configuration();

            Properties properties = new Properties();
            try (FileInputStream fIS = new FileInputStream(PROP_PATH)) {
                properties.load(fIS);
            } catch (IOException e) {
                e.printStackTrace();
            }

            configuration.setProperties(properties);
            configuration.addAnnotatedClass(User.class);


            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

}
