
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.courses.CourseLecture;
import acme.entities.courses.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerLectureRepository extends AbstractRepository {

	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :id")
	Collection<Lecture> findLecturesByCourseId(int id);

	@Query("select l from Lecture l where l.id = :id")
	Lecture findOneLectureById(int id);

	@Query("SELECT c FROM Course c WHERE c.id = :id")
	Course findCourseById(int id);

	@Query("select cl from CourseLecture cl where cl.course.id = :id")
	CourseLecture findCourseLectureByCourseId(int id);

	@Query("select cl from CourseLecture cl where cl.lecture.id = :id")
	CourseLecture findCourseLectureByLectureId(int id);
}
