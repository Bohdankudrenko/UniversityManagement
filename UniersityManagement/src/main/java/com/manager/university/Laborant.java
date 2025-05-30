package com.manager.university;

public class Laborant extends Lecturer{

    public Laborant() {
        super();
    }

    public Laborant(String first_name, String last_name, String email, String department, boolean isLaborant){
        super(first_name,last_name,email, department, isLaborant);
    }

    @Override
    public String toString() {
        return "Laborant: " + super.toString();
    }
}