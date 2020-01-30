package com.example.curbside;

public class DbConnection {

    private DbConnection db;

    // we don't want multiple connections at once, so use getInstance() to get this object
    private DbConnection() { }

    public static final String LOGIN_URL = "https://cgi.sice.indiana.edu/~teamNN/login.php";
    public static final String REGISTER_URL = "https://cgi.sice.indiana.edu/~teamNN/register.php";

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
