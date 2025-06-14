package com.manager.university;

public class Employee extends Person {
    protected String position;

    public Employee() {
        super();
    }

    public Employee(String firstName, String lastName, String email, String position) {
        super(firstName, lastName, email);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}