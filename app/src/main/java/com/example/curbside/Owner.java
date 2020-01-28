package com.example.curbside;

public class Owner extends Vendor {

    private String name;

    public Owner(User user) {
        super(user);
        this.setPermissions(3);
    }
}
