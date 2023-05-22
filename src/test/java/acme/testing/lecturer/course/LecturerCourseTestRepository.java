
package acme.testing.lecturer.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerCourseTestRepository extends AbstractRepository {

	@Query("SELECT c from Course c WHERE c.lecturer.name=:name")
	Collection<Course> findCourseByLecturerName(String name);

}
