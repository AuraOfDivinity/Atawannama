package com.example.atawannamaapp;

public class Operation {

    @com.google.gson.annotations.SerializedName("id")
    String opID;

    @com.google.gson.annotations.SerializedName("operation_employee")
    String opEmployee;

    @com.google.gson.annotations.SerializedName("operation_start")
    String opStartTime;

    @com.google.gson.annotations.SerializedName("operation_end")
    String opEndTime;

    @com.google.gson.annotations.SerializedName("operation_location")
    String opLocation;


    public Operation(String opID, String opEmployee, String opStartTime, String opEndTime, String opLocation) {
        this.opID = opID;
        this.opEmployee = opEmployee;
        this.opStartTime = opStartTime;
        this.opEndTime = opEndTime;
        this.opLocation = opLocation;
    }

    public Operation(){}

    public String getOpID() {
        return opID;
    }

    public void setOpID(String opID) {
        this.opID = opID;
    }

    public String getOpEmployee() {
        return opEmployee;
    }

    public void setOpEmployee(String opEmployee) {
        this.opEmployee = opEmployee;
    }

    public String getOpStartTime() {
        return opStartTime;
    }

    public void setOpStartTime(String opStartTime) {
        this.opStartTime = opStartTime;
    }

    public String getOpEndTime() {
        return opEndTime;
    }

    public void setOpEndTime(String opEndTime) {
        this.opEndTime = opEndTime;
    }

    public String getOpLocation() {
        return opLocation;
    }

    public void setOpLocation(String opLocation) {
        this.opLocation = opLocation;
    }
}
