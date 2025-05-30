package com.manager.university;
import com.manager.university.Person;

public class Lecturer extends Person {
    String department;
    boolean isLaborant;

    public Lecturer(){
        super();
    }

    public Lecturer(String first_name, String last_name, String email, String department, boolean isLaborant){
        super(first_name,last_name,email);
        this.department = department;
        this.isLaborant = isLaborant;
    }

    public String getDepartment() {
        return department;
    }

    public boolean isLaborant() {
        return isLaborant;
    }

    public void setLaborant(boolean laborant) {
        isLaborant = laborant;
    }

    @Override
    public String toString(){
        return "Lecturer: "+ super.toString() +", Department: "+ department +", Laborant: "+ isLaborant;
    }
}
