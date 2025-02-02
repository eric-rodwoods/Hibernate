package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Session session;
    public UserDaoHibernateImpl() { this.session = Util.getSession();  }

    @Override
    public void createUsersTable() {
        try{
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(Queries.CREATE_TABLE.QUERY, User.class).executeUpdate();
            transaction.commit();
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try{
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(Queries.DROP_TABLE.QUERY, User.class).executeUpdate();
            transaction.commit();
            session.clear();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try{
            Transaction transaction = session.beginTransaction();
            session.persist(new User(name, lastName, age));
            transaction.commit();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try{
            Transaction transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        return session.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public void cleanUsersTable() {
        try{
            Transaction transaction = session.beginTransaction();
            session.createMutationQuery("DELETE FROM User u").executeUpdate();
            transaction.commit();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
