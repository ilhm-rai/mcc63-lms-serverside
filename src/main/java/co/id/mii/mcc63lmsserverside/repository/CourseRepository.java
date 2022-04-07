/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.repository;

import co.id.mii.mcc63lmsserverside.model.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Agung
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

  @Query(
      value = "SELECT c.* FROM course AS c INNER JOIN enrollment AS e ON e.course_id = c.id WHERE e.user_id = ?1 AND e.payment_status = 1",
      nativeQuery = true)
  Optional<List<Course>> findMyCourse(Long userId);

  @Query(value = "SELECT * FROM COURSE LIMIT ?1", nativeQuery = true)
  List<Course> getCourseLimit(Long limit);
}
