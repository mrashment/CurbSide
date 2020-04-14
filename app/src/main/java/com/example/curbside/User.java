package com.example.curbside;

import android.util.Log;

import java.util.ArrayList;

public abstract class User {

     private static final String TAG = "User";
     private int id;

     public int getId() {
          return id;
     }

     public void setId(int id) {
          this.id = id;
     }

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

     public void setFavIds(Integer[] favIds) {
          this.favIds = favIds;
     }

     public ArrayList<Truck> getFavTrucks() {
          return favTrucks;
     }

     public void setFavTrucks(ArrayList<Truck> favTrucks) {
          this.favTrucks = favTrucks;
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
          result += "Id: " + this.id;
          result += "\nName: " + this.name;
          result += "\nEmail: " + this.email;
          result += "\nRewards: " + this.rewards;
          result += "\nPermission Level: " + this.permissions;
          return result;
     }
}
