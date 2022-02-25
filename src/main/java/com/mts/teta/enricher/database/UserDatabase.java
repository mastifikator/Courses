package com.mts.teta.enricher.database;

import com.mts.teta.enricher.models.User;

public interface UserDatabase {
    void addUser(String key, User user);

    User getUser(String key);

    boolean checkUser(String key);
}
