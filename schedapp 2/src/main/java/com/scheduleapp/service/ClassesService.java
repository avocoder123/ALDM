package com.scheduleapp.service;
import com.scheduleapp.model.classes;
import com.scheduleapp.model.courses;
import com.scheduleapp.repository.ClassesRepository;
import com.scheduleapp.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ClassesService {
    private final ClassesRepository classesRepository;
    private final UsersRepository usersRepository;
    public ClassesService(ClassesRepository classesRepository, UsersRepository usersRepository){
        this.classesRepository = classesRepository;
        this.usersRepository = usersRepository;
    }

    public List<classes> getClasses(){
        return classesRepository.getClasses();
    }

    public void addUser(String user,String course){
        classesRepository.addUser(user,course);
    }

    public List<classes> getSearchClasses(String field, String search, int f1, int f2, String userID){
        //get list that contains search term
        List<classes> classlistfull = classesRepository.getClasses();
        List<classes> classlist = new ArrayList<>();

        if(search.equals("")){
            classlist =classlistfull;
        }else if(field.equals("prof")){
            for(classes theclass: classlistfull){
                if(theclass.getProfessor().toLowerCase().contains(search.toLowerCase())){
                    classlist.add(theclass);
                }
            }
        }else if(field.equals("classid")){
            for(classes theclass: classlistfull){
                if(theclass.getCourse_ID().getCourse_ID().toLowerCase().contains(search.toLowerCase())){
                    classlist.add(theclass);
                }
            }
        }else if(field.equals("coursename")){
            for(classes theclass: classlistfull){
                if(theclass.getCourse_ID().getCourse_title().toLowerCase().contains(search.toLowerCase())){
                    classlist.add(theclass);
                }
            }
        }

        List<classes> notfull = new ArrayList<>();
        //remove full
        if(f1 == 1){
            for(classes aclass : classlist){
                if(classesRepository.checkCourseSpace(aclass.getClass_ID())){
                    notfull.add(aclass);
                }
            }
        }else{
            notfull = classlist;
        }
        //remove prereq fail
        List<classes> classlistfiltered = new ArrayList<>();
        String[] tokenrecords;
        String[] tokengrades;
        String studentrecord = usersRepository.findUserRecords(userID);
        String studentgrade = usersRepository.findUserGrades(userID);
        if(studentrecord==null){
            tokenrecords = new String[0];
            tokengrades = new String[0];
        }else{
            tokenrecords = studentrecord.split(" ");
            tokengrades = studentgrade.split(" ");
        }
        if(f2 == 1){
            for(classes aclass : notfull){
                String[] prereqs = classesRepository.getCoursePrereqs(aclass.getCourse_ID().getCourse_ID());
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
                    classlistfiltered.add(aclass);
                }
            }
        }else{
            classlistfiltered = notfull;
        }
        return classlistfiltered;
    }

    public List<classes> getSortedClasses(int sortBy){
        List<classes> sortedList = getClasses();
        switch(sortBy) {
            case 1:
                Collections.sort(sortedList, Comparator.comparing(c -> c.getCourse_ID().getCourse_ID()));
                break;
            case 2:
                Collections.sort(sortedList, Comparator.comparing(c -> c.getCourse_ID().getCourse_title()));
                break;
            case 3:
                Collections.sort(sortedList, Comparator.comparing(c -> c.getCourse_ID().getDepartment()));
                break;
            case 4:
                Collections.sort(sortedList, Comparator.comparing(c -> c.getOpenSlots()));
                break;
            case 5:
                Collections.sort(sortedList, Comparator.comparing(c -> c.getProfessor()));
                break;
        }
        return sortedList;
    }

    public void removeStudent(String user, String course){
        classesRepository.removeStudent(user,course);
    }

    public classes getTheClass(String course_ID){
        classes theClassResult = classesRepository.getTheClass(course_ID);
        return theClassResult;
    }

    public void deleteClass(String classid){
        classesRepository.deleteClass(classid);
    }

    //Michael additions
    //call class repo functions and here is where i'll do the checks
    //getclascourse_id from jparepo


    public void editCap(int capacity, String class_ID) {
        if (isGoodCap(capacity)) {
            classesRepository.editCap(capacity, class_ID);
        }
    }
    public void editProf(String professor, String class_ID){
        classesRepository.editProf(professor, class_ID);
    }

    public void addClass(String class_ID, String course_ID, int capacity, String professor) {

        int class_number = Integer.parseInt(course_ID.substring(course_ID.length() - 3));
        if(0 <= class_number && class_number <= 999){
            classesRepository.addClass(class_ID, course_ID, capacity, professor);
        }
    }

    public boolean isGoodCap(int capacity){
        return 0 <= capacity && capacity <= 100;
    }

    public List<String> getCoursesList() {
        return classesRepository.getCourseList();
    }

}
