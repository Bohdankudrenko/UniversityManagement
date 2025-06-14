package com.manager.university;

public class Laborant extends Employee {
    private String laboratoryName;

    public Laborant() {
        super();
    }

    public Laborant(String firstName, String lastName, String email, String position, String laboratoryName) {
        super(firstName, lastName, email, position);
        this.laboratoryName = laboratoryName;
    }

    public String getLaboratoryName() {
        return laboratoryName;
    }

    public void setLaboratoryName(String laboratoryName) {
        this.laboratoryName = laboratoryName;
    }
}