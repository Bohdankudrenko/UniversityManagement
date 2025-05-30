package com.manager.dao;

import java.util.ArrayList;
import java.util.List;
import com.manager.university.Student;
import com.manager.server.DatabaseConnector;
import java.sql.*;

public class StudentDao {
    // create
        public void add(Student student) {
            String sql = "INSERT INTO student (first_name, last_name, email, major, graduation_end) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = DatabaseConnector.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, student.getFirst_name());
                pstmt.setString(2, student.getLast_name());
                pstmt.setString(3, student.getEmail());
                pstmt.setString(4, student.getMajor());
                pstmt.setDate(5, Date.valueOf(student.getGraduation_end()));
                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    // Read (by ID)
    public Student getById(int id) {
        String sql = "SELECT * FROM student WHERE student_id = ?";
        Student student = null;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                student = new Student();
              //  student.setStudent_id(rs.getInt("student_id"));
             //  student.setLast_name(rs.getString("last_name"));
             //   student.setEmail(rs.getString("email"));
               // student.setMajor(rs.getString("major"));
              //  student.setGraduation_end(rs.getDate("graduation_end").toLocalDate());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return student;
    }

    // Read (all)
    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM student";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Student student = new Student();
            //    student.setStudent_id(rs.getInt("student_id"));
             //   student.setFirst_name(rs.getString("first_name"));
             //   student.setLast_name(rs.getString("last_name"));
             //   student.setMajor(rs.getString("major"));
              //  student.setGraduation_end(rs.getDate("graduation_end").toLocalDate());
                students.add(student);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return students;
    }

    // Update
    public void update(Student student) {
        String sql = "UPDATE student SET first_name = ?, last_name = ?, email = ?, major = ?, graduation_end = ? WHERE student_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getFirst_name());
            pstmt.setString(2, student.getLast_name());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getMajor());
            pstmt.setDate(5, Date.valueOf(student.getGraduation_end()));
          //  pstmt.setInt(6, student.getStudent_id());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Delete
    public void delete(int id) {
        String sql = "DELETE FROM student WHERE student_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
