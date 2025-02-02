package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 16);
        userService.saveUser("Petr", "Petrov", (byte) 17);
        userService.saveUser("Maksim", "Maksimov", (byte) 18);
        userService.saveUser("Egor", "Egorov", (byte) 12);
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
            if(user.getName().equals("Ivan")) {
                userService.removeUserById(user.getId());
            }
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.finishSession();
    }
}
