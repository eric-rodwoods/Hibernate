package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        try{
            if(connection == null || connection.isClosed()) connection = buildConnection();
        } catch(SQLException e){
            System.out.println("Error while creating Connection: "+e.getMessage());
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
}
