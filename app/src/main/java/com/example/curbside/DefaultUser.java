package com.example.curbside;

public class DefaultUser implements User {

    private String name;
    private String email;
    private int permissions = 1;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPermissions() {
        return this.permissions;
    }
}
