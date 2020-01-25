package com.example.curbside;

public class Vendor extends Decorator {

    private String name;
    private String email;

    public Vendor(User user) {
        super(user);
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    public int getPermissions() {
        return super.getPermissions() + 1;
    }
}
