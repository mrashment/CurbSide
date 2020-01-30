package com.example.curbside;

public class DefaultUser implements User {

    private String name;
    private String email;
    private int permissions = 1;

    public DefaultUser() { }

    // used when changing a vendor back to a default user
    public DefaultUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }

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
    public void setPermissions(int i) {this.permissions = i;}

    public static void main(String[] args) {
        User a = new DefaultUser();
        System.out.println(a.getPermissions());
        a = new Employee(a);
        System.out.println(a.getPermissions());
        a = new Owner(a);
        System.out.println(a.getPermissions());
        a = new Employee(a);
        System.out.println(a.getPermissions());
    }
}
