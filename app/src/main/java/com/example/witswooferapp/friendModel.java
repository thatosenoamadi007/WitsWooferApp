package com.example.witswooferapp;

public class friendModel {
    String Email;
    String Degree;

    public friendModel(){

    }

    public friendModel(String Email, String Degree) {
        this.Email= Email;
        this.Degree=Degree;
    }



    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDegree() {
        return Degree;
    }

    public void setDegree(String degree) {
        Degree = degree;
    }
}
