package com.raksha.raksha;

public class UserHelperClass {
    String name;
    String address;
    String bg;
    String allergies;
    String medications;
    String mail;
    String phone;
    String password;
    String pin;
    String od;
    int count;

    public UserHelperClass() {
    }

    public UserHelperClass(String name, String password, String email, String phone, String address, String bg, String allergies, String medications, String pin, String od,int count) {
        this.name = name;
        this.password = password;
        this.mail = email;
        this.phone = phone;
        this.address = address;
        this.bg = bg ;
        this.allergies = allergies ;
        this.medications = medications ;
        this.pin = pin ;
        this.od = od ;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getOd() {
        return od;
    }

    public void setOd(String od) {
        this.od = od;
    }
}
