
package acme.features.lecturer.courseLecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;

import acme.entities.courses.Course;
import acme.entities.courses.CourseLecture;
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

	@Query("select c from Course c where c.id = :id")
	Course findCourseById(int id);

	@Query("select l from Lecture l where l.id = :id")
	Lecture findLectureById(int id);

	@Query("select cl from CourseLecture cl where cl.course.id = :courseId and cl.lecture.id = :lectureId")
	Optional<CourseLecture> findCourseLectureByLectureAndCourseId(int courseId, int lectureId);
}
