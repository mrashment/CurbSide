package com.example.curbside;

public abstract class User {
     String name;
     String email;
     int rewards;
     int permissions;
     int companyID = -1;


     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public String getEmail() {
          return email;
     }

     public void setEmail(String email) {
          this.email = email;
     }

     public int getRewards() {
          return rewards;
     }

     public void setRewards(int rewards) {
          this.rewards = rewards;
     }

     public int getPermissions() {
          return permissions;
     }

     public void setPermissions(int permissions) {
          this.permissions = permissions;
     }
     public int getCompanyID() {
          return this.companyID;
     }
     public void setCompanyID(int ID) {
          this.companyID = ID;
     }

     @Override
     public String toString() {
          String result = "";
          result += "Name: " + this.name;
          result += "\nEmail: " + this.email;
          result += "\nRewards: " + this.rewards;
          result += "\nPermission Level: " + this.permissions;
          return result;
     }
}
