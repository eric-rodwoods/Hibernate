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
    private Connection connection;

    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        connection = Util.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(Queries.CREATE_TABLE.QUERY)){
            ps.executeUpdate();
        } catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        connection = Util.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(Queries.DROP_TABLE.QUERY)){
            ps.executeUpdate();
        } catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        connection = Util.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(Queries.SAVE.QUERY)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) System.out.println("User с именем – "+rs.getString(1)+" добавлен в базу данных");
            rs.close();
        } catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        connection = Util.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(Queries.REMOVE.QUERY)){
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        connection = Util.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(Queries.GET_ALL.QUERY)){
            ResultSet rs = ps.executeQuery();
            List<User> users = new ArrayList<>();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getLong(1));
                user.setName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setAge(rs.getByte(4));
                users.add(user);
            }
            rs.close();
            return users;
        } catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

    public void cleanUsersTable() {
        connection = Util.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(Queries.CLEAN_TABLE.QUERY)){
            ps.executeUpdate();
        } catch(SQLException | NullPointerException e){
            e.printStackTrace();
        }
    }
}
