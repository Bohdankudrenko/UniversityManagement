package com.manager.university;

import java.time.LocalDate;

public class Student extends Person {
    private String major;
    private LocalDate graduationEnd;

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public LocalDate getGraduationEnd() { return graduationEnd; }
    public void setGraduationEnd(LocalDate graduationEnd) { this.graduationEnd = graduationEnd; }
}