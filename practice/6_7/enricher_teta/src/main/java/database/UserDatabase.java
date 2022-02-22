package database;

import models.User;

public interface UserDatabase {
    void addUser(String key, User user);
    User getUser(String key);
    boolean checkUser(String key);
}
