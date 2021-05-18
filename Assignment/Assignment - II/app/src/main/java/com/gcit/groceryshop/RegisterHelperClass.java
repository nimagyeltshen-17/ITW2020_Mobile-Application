package com.gcit.groceryshop;

public class RegisterHelperClass {
    //All variable declaration
    private String FullName, Email, License, Address, Password;
    public RegisterHelperClass(){

    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLicense() {
        return License;
    }

    public void setLicense(String license) {
        License = license;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public RegisterHelperClass(String FullName, String Email, String License, String Address, String Password){
        this.FullName = FullName;
        this.Email = Email;
        this.License = License;
        this.Address = Address;
        this.Password = Password;
    }
}
