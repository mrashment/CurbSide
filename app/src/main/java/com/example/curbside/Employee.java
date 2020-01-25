package com.example.curbside;

public class Employee extends Vendor {


    public Employee(User user) {
        super(user);
        this.setPermissions(2);
    }

}
