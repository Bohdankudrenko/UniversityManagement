package com.manager.university;

public class Employee extends Person{

    public Employee() {
        super();
    }

    public Employee(String first_name, String last_name, String email) {
        super(first_name, last_name, email);
    }

    @Override
    public String toString() {
        return "Employee " + super.toString();
    }
}
