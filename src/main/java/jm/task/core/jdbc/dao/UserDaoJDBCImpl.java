package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();
    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS user (id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    " name VARCHAR(128), lastName VARCHAR(128),age SMALLINT)";
    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS user";
    private static final String INSERT_USER_SQL =
            "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)";
    private static final String DELETE_USER_SQL = "DELETE FROM user WHERE id = ?";
    private static final String FIND_ALL_USER_SQL = "SELECT id, name, lastName, age FROM user";
    private static final String CLEAN_USERS_SQL = "TRUNCATE TABLE user";

    public UserDaoJDBCImpl() {

    }

    private void duplicateMethod(String sqlQuery) {
        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            connection.setAutoCommit(false);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {
        duplicateMethod(CREATE_TABLE_SQL);
    }

    public void dropUsersTable() {
        duplicateMethod(DROP_TABLE_SQL);
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL)) {
            connection.setAutoCommit(false);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_USER_SQL)) {
            connection.setAutoCommit(false);
            ps.setLong(1, id);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_USER_SQL)) {
            connection.setAutoCommit(false);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getByte("age")
                ));
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        duplicateMethod(CLEAN_USERS_SQL);
    }
}
