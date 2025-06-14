package com.manager.university;

public class Library extends com.manager.university.Campus {

    public Library() {
        super();
    }

    public Library(String campusName, String adres) {
        super(campusName, adres);
    }

    @Override
    public String toString() {
        return "Library: " + super.toString();
    }

}
