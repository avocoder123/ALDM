package com.scheduleapp.service;

import com.scheduleapp.model.classes;
import com.scheduleapp.model.users;
import com.scheduleapp.repository.ClassesRepository;
import com.scheduleapp.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final ClassesRepository classesRepository;

    public UsersService(UsersRepository usersRepository, ClassesRepository classesRepository){
        this.classesRepository = classesRepository;
        this.usersRepository = usersRepository;
    }

    public List<classes> getSchedule(String id){
        return usersRepository.getSchedule(id);
    }

    public void removeClass(String user, String course){
        usersRepository.updateSchedule(user, course);
    }

    public List<users> getStudents(String course_ID){
        //get students and make them into a list
        String stringStudents = usersRepository.getStudents(course_ID);
        //chop up string and put it into a list of students
        List<users> listOfStudents = new ArrayList<>();
        //returns empty list if no students are found
        if(stringStudents.length() <=1){
            return listOfStudents;
        }
        String[] students = stringStudents.split(" ");
        for(int i = 0; i < students.length; i++) {
            if(!students[i].equals("")) {
                listOfStudents.add(usersRepository.getAStudent(students[i]));
            }
        }
        return listOfStudents;
    }

    public boolean addCourse(String user, String course){
        //checks users current courses to make sure no duplicates are added
        String[] tokens = usersRepository.getclassString(user).split(" ");
        String[] courseids = new String[tokens.length];
        String courseid = classesRepository.getClassCourseid(course);
        for(int i =0; i < tokens.length; i++){
            courseids[i] = classesRepository.getClassCourseid(tokens[i]);
        }
        for(int f =0; f < tokens.length; f++) {
            if(courseids[f] == null){
                continue;
            }
            if(courseids[f].equals(courseid)){
                return false;
            }
        }
        //check for course space
        if(!classesRepository.checkCourseSpace(course)){
            return false;
        }

        //check here for prereqs
        //get student records
        String studentrecord = usersRepository.findUserRecords(user);
        String studentgrade = usersRepository.findUserGrades(user);
        String[] tokenrecords;
        String[] tokengrades;
        if(studentrecord==null){
            tokenrecords = new String[0];
            tokengrades = new String[0];
        }else{
            tokenrecords = studentrecord.split(" ");
            tokengrades = studentgrade.split(" ");
        }
        //get course prereq
        String[] prereqs = classesRepository.getCoursePrereqs(courseid);
        //compare all token records with course prereqs, must be in records without d or f
        boolean meetsreq1 = true, meetsreq2 = true;
        if(prereqs[0] != null){
            meetsreq1=false;
        }
        if(prereqs[1] != null){
            meetsreq2=false;
        }
        for(int s = 0; s < tokenrecords.length; s++){
            if(!tokengrades[s].equals("D") && !tokengrades[s].equals("F")){
                if(prereqs[0] != null){
                    if(prereqs[0].equals(tokenrecords[s])){
                        meetsreq1 = true;
                    }
                }
                if(prereqs[1] != null){
                    if(prereqs[1].equals(tokenrecords[s])){
                        meetsreq2 = true;
                    }
                }
            }

        }
        if(meetsreq1 && meetsreq2){
            usersRepository.addCourse(user, course);
            return true;
        }
        return false;
    }

    public boolean addCourseOverride(String student, String classid){
        String[] tokens = usersRepository.getclassString(student).split(" ");
        String[] courseids = new String[tokens.length];
        String courseid = classesRepository.getClassCourseid(classid);
        for(int i =0; i < tokens.length; i++){
            courseids[i] = classesRepository.getClassCourseid(tokens[i]);
        }
        for(int f =0; f < tokens.length; f++) {
            if(courseids[f] == null){
                continue;
            }
            if(courseids[f].equals(courseid)){
                return false;
            }
        }
        usersRepository.addCourse(student, classid+ " ");
        return true;
    }

    public boolean checkUserExists(String SID){
        if(usersRepository.getAStudent(SID) == null){
            return false;
        }
        return true;
    }
}
