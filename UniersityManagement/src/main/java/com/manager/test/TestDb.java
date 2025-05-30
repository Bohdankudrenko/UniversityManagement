package com.manager.test;

import com.manager.dao.StudentDao ;
import com.manager.university.Student;

import java.time.LocalDate;
import java.util.List;

public class TestDb {

    public static void main(String[] args) {
        // Створюємо екземпляр нашого DAO
        StudentDao studentDAO = new StudentDao();

        System.out.println("test db");

        // 1. Тест операції CREATE (Створення)
        System.out.println("\n1. attempt to add student...");
        Student newStudent = new Student(
                "Nameee",
                "Lastnamee",
                "qwerty@example.com",
                "Math",
                LocalDate.of(2026, 6, 30)
        );
        studentDAO.add(newStudent);
        System.out.println("done!");


    }
}