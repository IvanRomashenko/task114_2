package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/pp113jdbs";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        Connection conn;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to database");
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error connecting to database");
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, DRIVER);
        properties.put(Environment.URL, URL);
        properties.put(Environment.USER, USERNAME);
        properties.put(Environment.PASS, PASSWORD);
        configuration.addProperties(properties);
        configuration.addAnnotatedClass(User.class);
        System.out.println("Connected to database");
        return configuration.buildSessionFactory();
    }
}
