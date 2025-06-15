package com.manager.dao;

import java.sql.SQLException;
import java.util.List;


public interface Dao<T> {
    void add(T entity) throws SQLException;
    T getById(int id) throws SQLException;
    List<T> getAll() throws SQLException;
    void update(T entity) throws SQLException;
    void delete(int id) throws SQLException;
}