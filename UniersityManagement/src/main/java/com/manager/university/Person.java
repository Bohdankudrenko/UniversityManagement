package com.manager.university;

public abstract class Person {
    protected int id;
    protected String first_name;
    protected String last_name;
    protected String email;

    public Person(){}

    public Person(String first_name, String last_name, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "id " + id + ", First name "+ first_name + ", Last name "+ last_name + ", Email "+ email;
    }

}
