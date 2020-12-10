package com.scheduleapp.jpa;

import com.scheduleapp.model.courses;
import org.springframework.data.repository.CrudRepository;

public interface JpacoursesRepository extends CrudRepository<courses, Long> {
}
