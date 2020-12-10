package com.scheduleapp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public class records {

    @Id
    private String student_ID;
    private String courses;
    private String grades;
    @OneToOne
    @PrimaryKeyJoinColumn
    private users users;

    public records() {
    }

    public String getStudent_ID() {
        return student_ID;
    }

    public void setStudent_ID(String student_ID) {
        this.student_ID = student_ID;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    public com.scheduleapp.model.users getUsers() {
        return users;
    }

    public void setUsers(com.scheduleapp.model.users users) {
        this.users = users;
    }
}
