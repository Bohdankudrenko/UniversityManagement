package com.manager.university;

public class AcademicBuilding extends Campus{

    public AcademicBuilding() {
        super();
    }

    public AcademicBuilding(String campusName, String adres) {
        super(campusName, adres);
    }

    @Override
    public String toString() {
        return "AcademicBuilding: " + super.toString();
    }
}
