package com.scheduleapp.repository;

import com.scheduleapp.jpa.JpaclassesRepository;
import com.scheduleapp.jpa.JpausersRepository;
import com.scheduleapp.model.classes;
import com.scheduleapp.model.users;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UsersRepository {
    private final JpausersRepository jpausersRepository;
    private final JpaclassesRepository repository;

    public UsersRepository(JpausersRepository jpausersRepository, JpaclassesRepository repository){
        this.jpausersRepository = jpausersRepository;
        this.repository = repository;
    }

    public String getclassString(String id){
        return jpausersRepository.findclassString(id);
    }

    public void addCourse(String user, String course){
        jpausersRepository.addCourse(user,course);
    }

    public List<classes> getSchedule(String id){
        String classString = jpausersRepository.findclassString(id);
        List<classes> schedule = new ArrayList<>();
        if(classString!=null) {
            String[] tokens = classString.split(" ");
            String aclass = "";
            for (int i = 0; i < tokens.length; i++) {
                aclass = tokens[i];
                classes bclass = repository.scheduleclass(aclass);
                if(bclass!=null) {
                    schedule.add(bclass);
                }
            }
        }
        return schedule;
    }

    public void updateSchedule(String user, String course){
        jpausersRepository.updateSchedule(user,course + " ");
    }

    public String findUserRecords(String user){
        return jpausersRepository.findUserRecords(user);
    }

    public String findUserGrades(String user){
        return jpausersRepository.findUserGrades(user);
    }

    public String getStudents(String course_ID){
        return repository.getStudents(course_ID);
    }

    public users getAStudent(String student_ID){
        return jpausersRepository.getAStudent(student_ID);
    }
}