package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
//        private final UserDao userDao = new UserDaoJDBCImpl();
    private final UserDao userDao = new UserDaoHibernateImpl();

    public void createUsersTable() {
        userDao.createUsersTable();
        System.out.println("Users table created");
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
        System.out.println("Users table dropped");
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
        System.out.println("User with name " + name + " saved");
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
        System.out.println("User with id " + id + " removed");
    }

    public List<User> getAllUsers() {
        List<User> users = userDao.getAllUsers();
        users.forEach(System.out::println);
        return users;
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
        System.out.println("Users table cleaned");
    }
}
