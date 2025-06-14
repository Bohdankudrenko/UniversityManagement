package com.manager.dao;

import com.manager.server.DatabaseConnector;
import com.manager.university.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao implements Dao<Student> {

    @Override
    public void add(Student student) throws SQLException {
        String sql = "INSERT INTO students(first_name, last_name, email, major, graduation_end) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getMajor());
            pstmt.setDate(5, Date.valueOf(student.getGraduationEnd()));
            pstmt.executeUpdate();
        }
    }

    @Override
    public Student getById(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToStudent(rs);
            }
        }
        return null;
    }

    @Override
    public List<Student> getAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DatabaseConnector.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }
        }
        return students;
    }

    @Override
    public void update(Student student) throws SQLException {
        String sql = "UPDATE students SET first_name = ?, last_name = ?, email = ?, major = ?, graduation_end = ? WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getMajor());
            pstmt.setDate(5, Date.valueOf(student.getGraduationEnd()));
            pstmt.setInt(6, student.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setEmail(rs.getString("email"));
        student.setMajor(rs.getString("major"));
        if (rs.getDate("graduation_end") != null) {
            student.setGraduationEnd(rs.getDate("graduation_end").toLocalDate());
        }
        return student;
    }
}