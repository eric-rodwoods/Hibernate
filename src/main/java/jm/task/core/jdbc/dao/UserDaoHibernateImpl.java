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
        Transaction transaction = null;
        try (Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(Queries.CREATE_TABLE.QUERY, User.class).executeUpdate();
            transaction.commit();
        } catch (NullPointerException ex) {
            System.out.println("Error while getting Session: " + ex.getMessage());
        } catch (IllegalStateException | RollbackException e) {
            assert transaction != null;
            transaction.rollback();
            System.out.println("Error while committing transaction: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try(Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(Queries.DROP_TABLE.QUERY, User.class).executeUpdate();
            transaction.commit();
            session.clear();
        } catch(NullPointerException ex) {
            System.out.println("Error while getting Session: "+ex.getMessage());
        } catch (IllegalStateException | RollbackException e) {
            assert transaction != null;
            transaction.rollback();
            System.out.println("Error while committing transaction: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try(Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.persist(new User(name, lastName, age));
            transaction.commit();
        } catch(NullPointerException ex) {
            System.out.println("Error while getting Session: "+ex.getMessage());
        } catch (IllegalStateException | RollbackException e) {
            assert transaction != null;
            transaction.rollback();
            System.out.println("Error while committing transaction: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try(Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
        } catch(NullPointerException ex) {
            System.out.println("Error while getting Session: "+ex.getMessage());
        } catch (IllegalStateException | RollbackException e) {
            assert transaction != null;
            transaction.rollback();
            System.out.println("Error while committing transaction: " + e.getMessage());
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
        Transaction transaction = null;
        try(Session session = Util.getSession()) {
            transaction = session.beginTransaction();
            session.createMutationQuery("DELETE FROM User u").executeUpdate();
            transaction.commit();
        } catch(NullPointerException ex) {
            System.out.println("Error while getting Session: "+ex.getMessage());
        } catch (IllegalStateException | RollbackException e) {
            assert transaction != null;
            transaction.rollback();
            System.out.println("Error while committing transaction: " + e.getMessage());
        }
    }
}
