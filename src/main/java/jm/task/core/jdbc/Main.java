package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {

    private static User[] users = new User[] {
            new User("Иван", "Иванов", (byte) 18),
            new User("Петр", "Петров", (byte) 34),
            new User("Савелий", "Савельев", (byte) 15),
            new User("Лариса", "Ивановна", (byte) 21)
    };

    public static void main(String[] args) {

        System.out.println(Util.getSessionFactoryHibernate());
        UserDaoHibernateImpl udao = new UserDaoHibernateImpl();
        udao.dropUsersTable();

//        UserServiceImpl usi = new UserServiceImpl();
//        usi.createUsersTable();
//
//        for (User user : users) {
//            usi.saveUser(user.getName(), user.getLastName(), user.getAge());
//            System.out.printf("User с именем – %s добавлен в базу данных\n", user.getName());
//        }
//
//        for (User user : usi.getAllUsers()) {
//            System.out.println(user.toString());
//        }
//        usi.cleanUsersTable();
//        usi.dropUsersTable();
    }
}
