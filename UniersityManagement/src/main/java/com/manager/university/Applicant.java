package com.manager.university;

import java.time.LocalDate;

public class Applicant extends com.manager.university.Person {
    String major;
    LocalDate graduation_end;

    public Applicant(){
        super();
    }

    public Applicant(String first_name, String last_name, String email, String major, LocalDate graduation_end) {
        super(first_name, last_name, email);
        this.major = major;
        this.graduation_end = graduation_end;
    }

    public String getMajor() {
        return major;
    }

    public LocalDate getGraduation_end() {
        return graduation_end;
    }

    @Override
    public String toString() {
        return "Applicant "+ super.toString()+ ", Major "+ major + ", End of graduation " + graduation_end;
    }
}
