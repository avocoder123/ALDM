package com.scheduleapp.model;

import javax.persistence.*;

@Entity
public class users {
    @Id
    private String user_ID;
    private String last_name;
    private String first_name;
    private String major;
    private String password;
    private int credits;
    private double GPA;
    @OneToOne(mappedBy = "users",cascade = CascadeType.ALL)
    private schedules schedule;
    @OneToOne(mappedBy = "users",cascade = CascadeType.ALL)
    private records records;

    public users() {
    }

    public String getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public schedules getSchedule() {
        return schedule;
    }

    public void setSchedule(schedules schedule) {
        this.schedule = schedule;
    }
}
