package com.manager.dao;

import com.manager.server.DatabaseConnector;
import com.manager.university.Lecturer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LecturerDao implements Dao<Lecturer> {

    @Override
    public void add(Lecturer lecturer) throws SQLException {
        String sql = "INSERT INTO lecturers(first_name, last_name, email, position, academic_rank) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, lecturer.getFirstName());
            pstmt.setString(2, lecturer.getLastName());
            pstmt.setString(3, lecturer.getEmail());
            pstmt.setString(4, lecturer.getPosition());
            pstmt.setString(5, lecturer.getAcademicRank());
            pstmt.executeUpdate();
        }
    }

    @Override
    public Lecturer getById(int id) throws SQLException {
        String sql = "SELECT * FROM lecturers WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToLecturer(rs);
            }
        }
        return null;
    }

    @Override
    public List<Lecturer> getAll() throws SQLException {
        List<Lecturer> lecturers = new ArrayList<>();
        String sql = "SELECT * FROM lecturers";
        try (Connection conn = DatabaseConnector.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lecturers.add(mapResultSetToLecturer(rs));
            }
        }
        return lecturers;
    }

    @Override
    public void update(Lecturer lecturer) throws SQLException {
        String sql = "UPDATE lecturers SET first_name = ?, last_name = ?, email = ?, position = ?, academic_rank = ? WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, lecturer.getFirstName());
            pstmt.setString(2, lecturer.getLastName());
            pstmt.setString(3, lecturer.getEmail());
            pstmt.setString(4, lecturer.getPosition());
            pstmt.setString(5, lecturer.getAcademicRank());
            pstmt.setInt(6, lecturer.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM lecturers WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    private Lecturer mapResultSetToLecturer(ResultSet rs) throws SQLException {
        Lecturer lecturer = new Lecturer();
        lecturer.setId(rs.getInt("id"));
        lecturer.setFirstName(rs.getString("first_name"));
        lecturer.setLastName(rs.getString("last_name"));
        lecturer.setEmail(rs.getString("email"));
        lecturer.setPosition(rs.getString("position"));
        lecturer.setAcademicRank(rs.getString("academic_rank"));
        return lecturer;
    }
}