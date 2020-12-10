package com.scheduleapp.model;

import javax.persistence.*;

@Entity
public class classes{
    @Id
    private String class_ID;
    @ManyToOne
    @JoinColumn(name="course_ID",nullable=false)
    private courses courses;
    private String semester;
    private int capacity;
    private int slots_Taken;
    private String professor;
    private String students;

    public classes() {
    }


    public String getClass_ID() {
        return class_ID;
    }

    public void setClass_ID(String class_ID) {
        this.class_ID = class_ID;
    }

    //does not return course id
    public courses getCourse_ID() {
        return courses;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getSlots_Taken() {
        return slots_Taken;
    }

    public void setSlots_Taken(int slots_Taken) {
        this.slots_Taken = slots_Taken;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getStudents() {
        return students;
    }

    public void setStudents(String students) {
        this.students = students;
    }

    public int getOpenSlots(){
        return capacity-slots_Taken;
    }
}
