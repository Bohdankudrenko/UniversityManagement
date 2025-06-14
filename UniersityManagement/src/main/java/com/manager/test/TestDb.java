package com.manager.test;

import com.manager.dao.StudentDao;
import com.manager.server.DatabaseConnector;
import com.manager.university.Student;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

/**
 * Class for testing and populating the database.
 * Can perform a full CRUD cycle for a single record,
 * as well as mass population of the database with test data.
 */
public class TestDb {

    /**
     * Main method to run.
     * By default, it populates the database with 50 students.
     * To run a CRUD test for a single student, comment out seedDatabase()
     * and uncomment runCrudTest().
     */
    public static void main(String[] args) throws SQLException {
        DatabaseConnector.initializeDatabase();
        System.out.println("Database initialized.");

        seedDatabase(50);
    }

    /**
     * Populates the database with the specified number of generated students.
     * Uses an efficient batch insert method.
     *
     * @param studentCount The number of students to create.
     */
    public static void seedDatabase(int studentCount) {
        System.out.println("\n--- Starting database population with test data (" + studentCount + " records) ---");
        String sql = "INSERT INTO students(first_name, last_name, email, major, graduation_end) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.connect()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                Random random = new Random();
                String[] firstNames = {"Ivan", "Petro", "Olena", "Mariia", "Andrii", "Sofiia", "Oleksandr", "Kateryna"};
                String[] lastNames = {"Ivanenko", "Petrenko", "Sydorenko", "Romanenko", "Kovalchuk", "Bondarenko"};
                String[] majors = {"Computer Science", "Law", "Economics", "Philology", "Engineering", "Medicine"};

                for (int i = 1; i <= studentCount; i++) {
                    String firstName = firstNames[random.nextInt(firstNames.length)];
                    String lastName = lastNames[random.nextInt(lastNames.length)];
                    String major = majors[random.nextInt(majors.length)];
                    String email = "student." + lastName.toLowerCase() + "." + i + "@example.com";
                    LocalDate graduationEnd = LocalDate.of(2025 + random.nextInt(4), random.nextInt(12) + 1, random.nextInt(28) + 1);

                    pstmt.setString(1, firstName);
                    pstmt.setString(2, lastName);
                    pstmt.setString(3, email);
                    pstmt.setString(4, major);
                    pstmt.setDate(5, Date.valueOf(graduationEnd));

                    pstmt.addBatch();
                }

                int[] result = pstmt.executeBatch();
                conn.commit();

                System.out.println("Successfully added " + result.length + " new records.");
            }

        } catch (SQLException e) {
            System.err.println("Error during batch data insertion:");
            e.printStackTrace();
        }
        System.out.println("--- Database population completed ---");
    }

    /**
     * Performs a full CRUD (Create, Read, Update, Delete) test cycle for a single student.
     */
    public static void runCrudTest() throws SQLException {
        StudentDao studentDao = new StudentDao();
        System.out.println("--- Starting CRUD test for a single student ---");

        // CREATE
        System.out.println("\n[CREATE] Attempting to add a new student...");
        Student newStudent = new Student();
        newStudent.setFirstName("Taras");
        newStudent.setLastName("Shevchenko");
        newStudent.setEmail("taras.kobzar@example.com");
        newStudent.setMajor("Literature");
        newStudent.setGraduationEnd(LocalDate.of(1861, 3, 10));
        studentDao.add(newStudent);
        System.out.println("Successfully added: " + newStudent.getFirstName() + " " + newStudent.getLastName());

        // READ
        System.out.println("\n[READ] Retrieving all students...");
        List<Student> allStudents = studentDao.getAll();
        if (allStudents.isEmpty()) {
            System.out.println("Error: student list is empty.");
            return;
        }
        allStudents.forEach(s -> System.out.println("  - Found: ID=" + s.getId() + ", Name=" + s.getFirstName()));
        int newId = allStudents.get(allStudents.size() - 1).getId();

        // READ BY ID
        System.out.println("\n[READ BY ID] Retrieving student by ID=" + newId);
        Student retrievedStudent = studentDao.getById(newId);
        System.out.println("Successfully found: " + retrievedStudent.getFirstName());

        // UPDATE
        System.out.println("\n[UPDATE] Updating major for student with ID=" + newId);
        retrievedStudent.setMajor("Fine Arts");
        studentDao.update(retrievedStudent);
        Student updatedStudent = studentDao.getById(newId);
        System.out.println("New major: " + updatedStudent.getMajor());

        // DELETE
        System.out.println("\n[DELETE] Deleting student with ID=" + newId);
        studentDao.delete(newId);
        Student deletedStudent = studentDao.getById(newId);
        if (deletedStudent == null) {
            System.out.println("Success! Student with ID=" + newId + " no longer exists.");
        } else {
            System.out.println("Error: Student with ID=" + newId + " still exists.");
        }

        System.out.println("\n--- CRUD test completed ---");
    }
}