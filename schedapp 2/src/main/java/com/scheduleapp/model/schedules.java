package com.scheduleapp.model;

import javax.persistence.*;

@Entity
public class schedules {
    @Id
    private String student_ID;
    private String classes;

    @OneToOne
    @PrimaryKeyJoinColumn
    private users users;

    public schedules() {
    }

    public String getStudent_ID() {
        return student_ID;
    }

    public void setStudent_ID(String student_ID) {
        this.student_ID = student_ID;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public users getUsers() {
        return users;
    }

    public void setUsers(users users) {
        this.users = users;
    }
}
