
package acme.features.authenticated.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedCourseRepository extends AbstractRepository {

	@Query("select c from Course c Where c.draft= false")
	Collection<Course> listAllCourse();

	@Query("select c from Course c where c.id = :id ")
	Course findOneCourseById(int id);

	@Query("select c from Course c where c.draft = false")
	Collection<Course> findManyCoursesByAvailability();

}
