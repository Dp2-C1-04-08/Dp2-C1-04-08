
package acme.features.lecturer.courseLecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.courses.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseLectureRepository extends AbstractRepository {

	@Query("SELECT l FROM Lecturer l WHERE l.id = :id")
	Lecturer findLecturerById(int id);

	@Query("select l from Lecture l where l.lecturer.id = :id")
	Collection<Lecture> findLecturesByLecturerId(int id);

	@Query("select c from Course c where c.lecturer.id = :id")
	Collection<Course> findCoursesByLecturerId(int id);
}
