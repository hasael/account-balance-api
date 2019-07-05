package services;

import domain.abstractions.UserService;
import domain.entities.User;

import java.util.ArrayList;
import java.util.Collection;

public class UserServiceImpl implements UserService {
    @Override
    public void addUser(User user) {

    }

    @Override
    public Collection<User> getUsers() {
        ArrayList<User> list = new ArrayList<>();
       // list.add(new User());
        list.add(new User("id1","username","lastname","email"));
        return list;
    }

    @Override
    public User getUser(String id) {
        return null;
    }

    @Override
    public User editUser(User user)  {
        return null;
    }

    @Override
    public void deleteUser(String id) {

    }

    @Override
    public boolean userExist(String id) {
        return false;
    }
}
