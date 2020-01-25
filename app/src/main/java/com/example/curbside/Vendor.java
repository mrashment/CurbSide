package com.example.curbside;

public class Vendor extends Decorator {


    public Vendor(User user) {
        super(user);
        this.setPermissions(2);
    }

}
