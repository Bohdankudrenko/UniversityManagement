package com.manager.university;

public abstract class Building {
    protected int id;

    public Building() {}

    @Override
    public String toString() {
        return "id: " + id;
    }
}