package com.example.curbside;

public class Owner extends Decorator {

    public Owner(User user) {
        super(user);
        this.setPermissions(3);
    }
}
