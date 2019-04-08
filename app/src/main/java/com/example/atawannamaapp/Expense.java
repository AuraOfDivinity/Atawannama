package com.example.atawannamaapp;

public class Expense {

    @com.google.gson.annotations.SerializedName("id")
    String expenseID;

    @com.google.gson.annotations.SerializedName("Auth")
    String expenseAuth;

    @com.google.gson.annotations.SerializedName("expenseCmt")
    String expenseComments;

    @com.google.gson.annotations.SerializedName("expenseAmt")
    double expenseAmount;



    public  Expense(){}

    public Expense( String expenseID, Double expenseAmount, String expenseComments) {
        this.expenseAmount = expenseAmount;
        this.expenseID = expenseID;

        this.expenseComments = expenseComments;
    }
    public String getExpenseAuth() {
        return expenseAuth;
    }

    public void setExpenseAuth(String expenseAuth) {
        this.expenseAuth = expenseAuth;
    }

    public Double getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(Double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(String expenseID) {
        this.expenseID = expenseID;
    }

    public String getExpenseComments() {
        return expenseComments;
    }

    public void setExpenseComments(String expenseComments) {
        this.expenseComments = expenseComments;
    }
}
