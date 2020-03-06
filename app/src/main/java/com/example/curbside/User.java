package com.example.curbside;

import java.util.ArrayList;

public abstract class User {
     private String name;
     private String email;
     private int rewards;
     private int permissions;
     private int companyID = -1;
     private Integer[] favIds;
     private ArrayList<Truck> favTrucks;

     public Integer[] getFavIds() {
          return favIds;
     }

     public void setFavIds(String[] stringIds) {
          this.favIds = new Integer[stringIds.length];
          for (int i = 0; i < stringIds.length; i++) {
               if (!stringIds[i].equals("")) {
                    int newid = Integer.parseInt(stringIds[i]);
                    this.favIds[i] = newid;
               }
          }
     }

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
