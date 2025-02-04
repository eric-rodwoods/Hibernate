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
    private final Connection conn;

    public UserDaoJDBCImpl() { this.conn = Util.getConnection(); }

    public void createUsersTable() {
        try{
            PreparedStatement ps = conn.prepareStatement(Queries.CREATE_TABLE.QUERY);
            ps.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try{
            PreparedStatement ps = conn.prepareStatement(Queries.DROP_TABLE.QUERY);
            ps.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try{
            PreparedStatement ps = conn.prepareStatement(Queries.SAVE.QUERY);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) System.out.println("User с именем – "+rs.getString(1)+" добавлен в базу данных");
            rs.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try{
            PreparedStatement ps = conn.prepareStatement(Queries.REMOVE.QUERY);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        try{
            PreparedStatement ps = conn.prepareStatement(Queries.GET_ALL.QUERY);
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
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void cleanUsersTable() {
        try{
            PreparedStatement ps = conn.prepareStatement(Queries.CLEAN_TABLE.QUERY);
            ps.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
