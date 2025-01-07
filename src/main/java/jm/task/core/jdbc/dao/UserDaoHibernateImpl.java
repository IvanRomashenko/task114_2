package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sf = Util.getSessionFactory();
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS user " +
            "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(128), lastName VARCHAR(128),age SMALLINT)";
    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS user";
    private static final String CLEAN_USERS_SQL = "TRUNCATE TABLE user";

    public UserDaoHibernateImpl() {

    }

    private void duplicateMethod(String sqlQuery) {
        Transaction transaction = null;
        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sqlQuery).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException();
        }
    }

    @Override
    public void createUsersTable() {
        duplicateMethod(CREATE_TABLE_SQL);

    }

    @Override
    public void dropUsersTable() {
        duplicateMethod(DROP_TABLE_SQL);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        }catch (HibernateException e){
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
//            users = session.createCriteria(User.class).list();
            users = session.createQuery("from User").list();
            transaction.commit();
        }catch (HibernateException e){
            if(transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        duplicateMethod(CLEAN_USERS_SQL);
    }
}
