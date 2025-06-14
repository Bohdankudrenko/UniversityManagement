package com.manager.university;

public class Campus extends com.menager.university.Building {
    String campusName;
    String adres;

    public Campus(){
        super();
    }

    public Campus(String campusName, String adres) {
        this.campusName = campusName;
        this.adres = adres;
    }

    public String getCampusName() {
        return campusName;
    }

    public String getAdres() {
        return adres;
    }

    @Override
    public String toString() {
        return "Campus: " + super.toString() + ", campusName: " + campusName + ", Adres: " + adres;
    }

}
