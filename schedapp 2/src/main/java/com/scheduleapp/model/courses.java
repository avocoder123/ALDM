package com.scheduleapp.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class courses {
    @Id
    private String course_ID;
    private String course_title;
    private String department;
    private String description;
    private int credits;
    private String prereq1;
    private String prereq2;
    private String corequisite;
    @OneToMany(mappedBy= "courses")
    private Set<classes> classes;

    public courses() {
    }

    public String getCourse_ID() {
        return course_ID;
    }

    public void setCourse_ID(String course_ID) {
        this.course_ID = course_ID;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getPrereq1() {
        return prereq1;
    }

    public void setPrereq1(String prereq1) {
        this.prereq1 = prereq1;
    }

    public String getPrereq2() {
        return prereq2;
    }

    public void setPrereq2(String prereq2) {
        this.prereq2 = prereq2;
    }

    public String getCorequisite() {
        return corequisite;
    }

    public void setCorequisite(String corequisite) {
        this.corequisite = corequisite;
    }

    public Set<com.scheduleapp.model.classes> getClasses() {
        return classes;
    }

    public void setClasses(Set<com.scheduleapp.model.classes> classes) {
        this.classes = classes;
    }
}
