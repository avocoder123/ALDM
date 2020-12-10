package com.scheduleapp.jpa;

import com.scheduleapp.model.classes;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface JpaclassesRepository extends CrudRepository<classes, Long> {

    @Query("select c FROM classes c where c.class_ID = :id")
    classes scheduleclass(@Param("id") String id);

    @Query(value = "Select prereq1 from courses where course_ID = :course")
    String getCoursePrereq1(@Param("course") String course);

    @Query(value = "Select prereq2 from courses where course_ID = :course")
    String getCoursePrereq2(@Param("course") String course);

    @Query(value = "Select students from classes where class_ID = :course")
    String getStudents(@Param("course") String course);

    @Query(value="Select capacity from classes where class_ID = :courseid", nativeQuery = true)
    int checkCourseTotal(@Param("courseid") String courseid);

    @Query(value="Select slots_taken from classes where class_ID = :courseid", nativeQuery = true)
    int checkCourseFull(@Param("courseid") String courseid);

    @Query(value ="Select course_ID from classes where class_ID = :classid", nativeQuery = true)
    String getClassCourseid(@Param("classid") String classid);

    @Modifying
    @Query(value ="UPDATE classes SET students = REPLACE(students,:user,' '), slots_taken=slots_taken-1 WHERE class_id=:course", nativeQuery = true)
    void removeStudent(@Param("user") String user, @Param("course") String course);

    @Modifying
    @Query(value="UPDATE classes SET classes.students = CONCAT(CONCAT(students,:user),' '),slots_taken=slots_taken+1 WHERE classes.class_id=:course",nativeQuery = true)
    void addUser(@Param("user") String user, @Param("course") String course);

    @Modifying
    @Query(value="Delete from classes where class_ID = :classid",nativeQuery = true)
    void deleteClass(@Param("classid") String classid);
    //Michael's Additions
    //
    //Edit class
    @Modifying
    @Query(value="UPDATE classes SET capacity=:capacity WHERE classes.class_ID=:class_ID", nativeQuery = true)
    void editCap(@Param("capacity") int capacity, @Param("class_ID") String class_ID);

    @Modifying
    @Query(value="UPDATE classes SET professor=:professor WHERE classes.class_ID=:course", nativeQuery = true)
    void editProf(@Param("professor") String professor, @Param("course") String class_ID);

    //Add class
    @Modifying
    @Query(value="INSERT INTO classes VALUES (:class_ID, :course_ID, 'SP2021', :capacity, '0', :professor, '')", nativeQuery = true)
    void addClass(@Param("class_ID") String class_ID, @Param("course_ID") String course_ID,@Param("capacity") int capacity, @Param("professor")
            String professor);


}