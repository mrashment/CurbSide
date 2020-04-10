package com.example.curbside;

import android.content.Context;
import android.database.DatabaseUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class DbConnection {
    private static final String TAG = "DbConnection";

    private static final DbConnection db = new DbConnection();
    private User user;
    private boolean isNull = true;

    // we don't want multiple connections at once, so use getInstance() to get this object
    private DbConnection() { }

    public static final String USER_INFO = "https://cgi.sice.indiana.edu/~team59/userinfo.php"; // gets the user info in form of JSON
    public static final String USER_ADD = "https://cgi.sice.indiana.edu/~team59/useradd.php"; // adds user to db
    public static final String NEARBY_TRUCKS = "https://cgi.sice.indiana.edu/~team59/nearbytrucks.php";
    public static final String FAVORITE_TRUCKS = "https://cgi.sice.indiana.edu/~team59/favoritetrucks.php";
    public static final String ADD_FAVORITE = "https://cgi.sice.indiana.edu/~team59/addfavorite.php";
    public static final String DELETE_FAVORITE = "https://cgi.sice.indiana.edu/~team59/deletefavorite.php";
    public static final String SEARCH_TRUCKS = "https://cgi.sice.indiana.edu/~team59/searchtrucks.php";


    public static DbConnection getInstance() {
        return db;
    }

    public void setUser(JSONObject json) {
        try {
            db.user = new DefaultUser();
            user.setId(json.getInt("id"));
            db.user.setEmail(json.getString("email"));
            db.user.setName(json.getString("name"));
            db.user.setPermissions(json.getInt("permission_lev"));
            db.user.setRewards(json.getInt("rewards"));
            db.user.setCompanyID(json.getInt("company_id"));
            Log.d(TAG, "setUser: User set in DbConnection");
            isNull = false;

            if (user.getPermissions() == 2)  {
                user = new Employee(user);
            } else if (user.getPermissions() == 3) {
                user = new Owner(user);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * calls user.toString()
     * @return a string displaying the user's info
     */
    public String printUserInfo() {
        return user.toString();
    }

    public User getUser() {
        return this.user;
    }

    public boolean userIsNull() {
        return isNull;
    }

    public void updateUserInfo() {

    }


}
