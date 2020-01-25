package com.example.curbside;

public class DbConnection {

    private DbConnection db;

    // we don't want multiple connections at once, so use getInstance() to get this object
    private DbConnection() { }

    public DbConnection getInstance() {
        if (db == null) {
            this.db = new DbConnection();
        }
        return this.db;
    }

    public void getUserInfo(String email) {

    }

    public void updateUserInfo(String email) {

    }
}
