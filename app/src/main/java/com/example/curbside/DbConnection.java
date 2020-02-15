package com.example.curbside;

import org.json.JSONException;
import org.json.JSONObject;

public class DbConnection {

    private static DbConnection db;
    private User user;

    // we don't want multiple connections at once, so use getInstance() to get this object
    private DbConnection() {
    }

//    public static final String LOGIN_URL = "https://cgi.sice.indiana.edu/~team59/login.php";
//    public static final String REGISTER_URL = "https://cgi.sice.indiana.edu/~team59/register.php";
    public static final String USER_INFO = "https://cgi.sice.indiana.edu/~team59/userinfo.php"; // gets the user info in form of JSON
    public static final String USER_ADD = "https://cgi.sice.indiana.edu/~team59/useradd.php"; // adds user to db

    public static DbConnection getInstance() {
        if (db == null) {
            db = new DbConnection();
        }
        return db;
    }
    public void setUser(JSONObject json) {
        try {
            user.setEmail(json.getString("email"));
            user.setName(json.getString("name"));
            user.setPermissions(json.getInt("permission_lev"));
            user.setRewards(json.getInt("rewards"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * calls user.toString()
     * @return a string displaying the user's info
     */
    public String getUserInfo() {
        return user.toString();
    }

    public void updateUserInfo() {

    }


}
