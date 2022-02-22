package database;

import models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserDatabase implements UserDatabase {

    private final Logger LOG = LoggerFactory.getLogger(InMemoryUserDatabase.class);
    private final ConcurrentHashMap<String, User> userDb = new ConcurrentHashMap<>();

    @Override
    public void addUser(String key, User user){
        userDb.put(key, user);
        LOG.debug("add user " + user);
    }

    @Override
    public User getUser(String key){
        return userDb.get(key);
    }

    @Override
    public boolean checkUser(String key){
        return userDb.containsKey(key);
    }
}
