package com.manager.university;

import java.time.LocalDate;

public class Student extends com.manager.university.Applicant {

    public Student() {
        super();
    }

    public Student(String first_name, String last_name, String email, String major, LocalDate graduation_end) {
        super(first_name, last_name, email, major, graduation_end);
    }

    @Override
    public String toString() {
        return "Student " + super.toString();
    }

    @Override
    public LocalDate getGraduation_end(){
        return this.graduation_end;
    }
}
