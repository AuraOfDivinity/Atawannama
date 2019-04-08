package com.example.atawannamaapp;

public class Employee {
    @com.google.gson.annotations.SerializedName("name")
    String Name;

    @com.google.gson.annotations.SerializedName("Uname")
    public String username;

    public String getPresent_status() {
        return present_status;
    }

    public void setPresent_status(String present_status) {
        this.present_status = present_status;
    }

    @com.google.gson.annotations.SerializedName("pw")
    public String password;

//    @com.google.gson.annotations.SerializedName("FirstName")
//    public String fName;

    @com.google.gson.annotations.SerializedName("id")
    String empID;


    @com.google.gson.annotations.SerializedName("clearance")
    String clearance;

    @com.google.gson.annotations.SerializedName("comments")
    String comments;

    @com.google.gson.annotations.SerializedName("present_status")
    String present_status;

    public Employee(){
        this.present_status = "absent";
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getClearance() {
        return clearance;
    }

    public void setClearance(String clearance) {
        this.clearance = clearance;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


//    public String getfName() {
//        return fName;
//    }
//
//    public void setfName(String fName) {
//        this.fName = fName;
//    }
//
//    public int getEmpID() {
//        return empID;
//    }
//
//    public void setEmpID(int empID) {
//        this.empID = empID;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
