package com.manager.university;

public class PublicArea extends com.manager.university.Campus {

    public PublicArea() {
        super();
    }

    public PublicArea(String campusName, String adres) {
        super(campusName, adres);
    }

    @Override
    public String toString() {
        return "PublicArea: " + super.toString();
    }

}
