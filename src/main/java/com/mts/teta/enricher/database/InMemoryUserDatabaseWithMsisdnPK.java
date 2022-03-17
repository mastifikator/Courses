package com.mts.teta.enricher.database;

import com.mts.teta.enricher.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserDatabaseWithMsisdnPK implements UserDatabase {

    private final Logger LOG = LoggerFactory.getLogger(InMemoryUserDatabaseWithMsisdnPK.class);
    private final ConcurrentHashMap<String, User> userDb = new ConcurrentHashMap<>();

    @Override
    public void addUser(String key, User user) {
        userDb.put(key, user);
        LOG.debug("add user " + user);
    }

    @Override
    public User getUser(String key) {
        return userDb.get(key);
    }

    @Override
    public boolean checkUser(String key) {
        return userDb.containsKey(key);
    }
}
