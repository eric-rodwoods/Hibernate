package jm.task.core.jdbc.dao;

import jakarta.persistence.RollbackException;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {
        try(Session session = Util.getSession()){
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(Queries.CREATE_TABLE.QUERY, User.class).executeUpdate();
            try{
                transaction.commit();
            } catch(IllegalStateException | RollbackException e) {
                transaction.rollback();
                System.out.println("Error while commiting transaction: "+e.getMessage());
            }
        } catch(NullPointerException ex) {
            System.out.println("Error while getting Session: "+ex.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = Util.getSession()){
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(Queries.DROP_TABLE.QUERY, User.class).executeUpdate();
            try{
                transaction.commit();
                session.clear();
            } catch(IllegalStateException | RollbackException e) {
                transaction.rollback();
                System.out.println("Error while commiting transaction: "+e.getMessage());
            }
        } catch(NullPointerException ex) {
            System.out.println("Error while getting Session: "+ex.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = Util.getSession()){
            Transaction transaction = session.beginTransaction();
            session.persist(new User(name, lastName, age));
            try{
                transaction.commit();
            } catch(IllegalStateException | RollbackException e) {
                transaction.rollback();
                System.out.println("Error while commiting transaction: "+e.getMessage());
            }
        } catch(NullPointerException ex) {
            System.out.println("Error while getting Session: "+ex.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = Util.getSession()){
            Transaction transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            try{
                transaction.commit();
            } catch(IllegalStateException | RollbackException e) {
                transaction.rollback();
                System.out.println("Error while commiting transaction: "+e.getMessage());
            }
        } catch(NullPointerException ex) {
            System.out.println("Error while getting Session: "+ex.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        try(Session session = Util.getSession()){
            return session.createQuery("SELECT u FROM User u", User.class).getResultList();
        } catch(NullPointerException ex) {
            System.out.println("Error while getting Session: "+ex.getMessage());
            return new ArrayList<User>();
        }
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getSession()){
            Transaction transaction = session.beginTransaction();
            session.createMutationQuery("DELETE FROM User u").executeUpdate();
            try{
                transaction.commit();
            } catch(IllegalStateException | RollbackException e) {
                transaction.rollback();
                System.out.println("Error while commiting transaction: "+e.getMessage());
            }
        } catch(NullPointerException ex) {
            System.out.println("Error while getting Session: "+ex.getMessage());
        }
    }
}
