
package acme.features.lecturer.course;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.courses.CourseLecture;
import acme.entities.courses.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerCourseRepository extends AbstractRepository {

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);

	@Query("select c from Course c where c.lecturer.id = :id")
	Collection<Course> findManyCoursesByLecturerId(int id);

	@Query("SELECT l FROM Lecturer l WHERE l.id = :id")
	Lecturer findLecturerById(int id);

	@Query("Select c From Course c where c.code = :code")
	Optional<Course> findOneCourseByCode(String code);

	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :id")
	Collection<Lecture> findLecturesByCourseId(int id);

	@Query("select cl from CourseLecture cl where cl.course.id = :id")
	CourseLecture findCourseLecturesByCourseId(int id);
	
	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :id")
	Collection<Lecture> haveLecturesById(int id);

}
