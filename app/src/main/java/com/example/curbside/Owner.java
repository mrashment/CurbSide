package com.example.curbside;

public class Owner extends Vendor {

    public Owner(User user) {
        super(user);
        this.setPermissions(3);
    }
}
