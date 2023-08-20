package application.dao;

import application.dto.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDAO {

    private List<User> userList;
    private Scanner s;

    public UserDAO(){
        userList = new ArrayList<User>();

        userList.add(new User("admin", "AdminEmail@gmail.com",
                "123"));
        userList.add(new User("customer", "CustomerEmail@gmail.com",
                "456"));

        s = new Scanner(System.in);
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public void insertUser(){
        s = new Scanner(System.in);

        User user1 = new User();

        System.out.print("\nEnter a Username: ");
        user1.setUsername(s.nextLine());

        s = new Scanner(System.in);
        System.out.print("\nEnter a Email ID: ");
        user1.setEmailId(s.next());

        s = new Scanner(System.in);
        System.out.print("\nEnter a Password: ");
        user1.setPassword(s.nextLine());

        userList.add(user1);

    }

}
