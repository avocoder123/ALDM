package com.scheduleapp.repository;


import com.scheduleapp.jpa.JpaclassesRepository;
import com.scheduleapp.jpa.JpacoursesRepository;
import com.scheduleapp.model.classes;
import com.scheduleapp.model.courses;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClassesRepository {

    private final JpaclassesRepository repository;
    private final JpacoursesRepository jpacoursesRepository;

    public ClassesRepository(JpaclassesRepository repository, JpacoursesRepository jpacoursesRepository){
        this.jpacoursesRepository = jpacoursesRepository;
        this.repository = repository;
    }

    public List<classes> getClasses(){
        return (List<classes>) repository.findAll();
    }

    public void removeStudent(String user,String course){
        repository.removeStudent(user + " ",course);
    }

    public void addUser(String user, String course){
        repository.addUser(user,course);
    }
    public String getClassCourseid(String classid){
        return repository.getClassCourseid(classid);
    }

    public boolean checkCourseSpace(String course){
        if((repository.checkCourseTotal(course)-repository.checkCourseFull(course))>0){
            return true;
        }
        return false;
    }

    public String[] getCoursePrereqs(String course){
        String[] prer = new String[2];
        prer[0] = repository.getCoursePrereq1(course);
        prer[1] = repository.getCoursePrereq2(course);
        return prer;
    }

    public classes getTheClass(String course_ID){
        classes theClassResult = repository.scheduleclass(course_ID);
        return theClassResult;
    }

    public void deleteClass(String classid){
        repository.deleteClass(classid);
    }

    //Michael additions
    //this is an intermediate step to connect Class Service to jpa repo
    //All calls of these methods in Class service should be the correct
    //

    //edit classes

    public void editCap(int capacity, String class_ID){
        repository.editCap(capacity,class_ID);
    }

    public void editProf(String professor, String class_ID){
        repository.editProf(professor, class_ID);
    }

    //add classes here

    public void addClass(String class_ID, String course_ID, int capacity, String professor){
        repository.addClass(class_ID, course_ID, capacity, professor);
    }

    public List<String> getCourseList(){
        List<String> list = new ArrayList<>();
        for(courses a: jpacoursesRepository.findAll()){
            list.add(a.getCourse_ID());
        }
        return list;
    }

}
