package com.manager.university;

public class Lecturer extends Employee {
    private String academicRank;

    public Lecturer() {
        super();
    }

    public Lecturer(String firstName, String lastName, String email, String position, String academicRank) {
        super(firstName, lastName, email, position);
        this.academicRank = academicRank;
    }

    public String getAcademicRank() {
        return academicRank;
    }

    public void setAcademicRank(String academicRank) {
        this.academicRank = academicRank;
    }
}