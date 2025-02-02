package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private final static String url = "jdbc:postgresql://localhost:5432/Test_db";
    private final static String username = "postgres";
    private final static String password = "postgres";
    private final static String driver = "org.postgresql.Driver";

    private static Connection connection = buildConnection();

    private static Connection buildConnection() {
        try{
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch(SQLException | ClassNotFoundException e){
            System.out.println("Error while creating Connection: "+e.getMessage());
            return null;
        }
    }
    public static Connection getConnection() {
        if(connection == null){
            connection = buildConnection();
        }
        return connection;
    }
    public static void finishConnection() {
        try{
            if(connection != null){
                connection.close();
            }
        } catch(SQLException e){
            System.out.println("Error while finishing Connection: "+e.getMessage());
        }
    }

    private static SessionFactory sessionFactory = buildSessionFactory();
    private static Session session = buildSession();

    private static SessionFactory buildSessionFactory() {
        try{
            Properties prop = new Properties();
            prop.setProperty("hibernate.connection.url", url);
            prop.setProperty("hibernate.connection.username", username);
            prop.setProperty("hibernate.connection.password", password);
            prop.setProperty("dialect", driver);
            prop.setProperty("hibernate.hbm2ddl.auto", "none");

            return new Configuration()
                    .addProperties(prop)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch(HibernateException e){
            System.out.println("Error while creating SessionFactory: "+e.getMessage());
            return null;
        }
    }
    private static Session buildSession() {
        try{
            if (sessionFactory == null) {
                sessionFactory = buildSessionFactory();
            }
            assert sessionFactory != null;
            return sessionFactory.openSession();
        } catch(HibernateException e){
            System.out.println("Error while creating Session: "+e.getMessage());
            return null;
        }
    }
    public static Session getSession(){
        if(session == null){
            session = buildSession();
        }
        return session;
    }
    public static void finishSession() {
        try{
            if(session != null){
                session.close();
            }
            if(sessionFactory != null){
                sessionFactory.close();
            }
        } catch(HibernateException e){
            System.out.println("Error while finishing Session: "+e.getMessage());
        }
    }
}
