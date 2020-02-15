package com.example.curbside;

public class DefaultUser extends User {

    private String name;
    private String email;
    private int rewards;
    private int permissions = 1;

    public DefaultUser() { }


    // used when changing a vendor back to a default user
    public DefaultUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.rewards = user.getRewards();
    }

    /**
     * retrieve the users info from the database
     */
    public void pullInfo() {

    }

    /**
     * update the database with new user info
     */
    public void updateInfo() {

    }

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
