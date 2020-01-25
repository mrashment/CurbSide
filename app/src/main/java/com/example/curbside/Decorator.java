package com.example.curbside;

public abstract class Decorator implements User {

    private User user;

    public Decorator(User user) {
        this.user = user;
    }

    public int getPermissions() {
        return this.user.getPermissions();
    }

}
