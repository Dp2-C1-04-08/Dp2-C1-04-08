
package acme.features.lecturer.lecture;

import java.util.Collection;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.courses.CourseLecture;
import acme.entities.courses.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerLectureRepository extends AbstractRepository {

	@Query("select cl.lecture from CourseLecture cl where cl.course.id = :id")
	Collection<Lecture> findLecturesByCourseId(int id);

	@Query("select l from Lecture l where l.lecturer.id = :id")
	Collection<Lecture> findManyLecturesByLecturerId(int id);

	@Query("select l from Lecture l where l.id = :id")
	Lecture findOneLectureById(int id);

	@Query("SELECT c FROM Course c WHERE c.id = :id")
	Course findCourseById(int id);

	@Query("SELECT l FROM Lecturer l WHERE l.id = :id")
	Lecturer findLecturerById(int id);

	@Query("select cl from CourseLecture cl where cl.course.id = :id")
	CourseLecture findCourseLectureByCourseId(int id);

	@Query("select cl from CourseLecture cl where cl.lecture.id = :id")
	CourseLecture findCourseLectureByLectureId(int id);

	@Query("SELECT cl.lecture.lectureType, COUNT(cl.lecture) FROM CourseLecture cl WHERE cl.course.id = :id GROUP BY cl.lecture.lectureType")
	Collection<Tuple> countLecturesGroupByType(int id);
}
