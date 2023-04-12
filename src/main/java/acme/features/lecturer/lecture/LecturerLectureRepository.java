
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.courses.Lecture;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Lecturer;

@Repository
public interface LecturerLectureRepository extends AbstractRepository {

	@Query("select l from Lecture l where l.id = :id")
	Lecture findOneLectureById(int id);

	@Query("select cl.lecture from CourseLecture cl where cl.course.lecturer.userAccount.id = :id")
	Collection<Lecture> findLecturesByLecturerId(int id);

	@Query("select cl.course.lecturer from CourseLecture cl where cl.course.lecturer.userAccount.id = :id")
	Lecturer findLecturerByLectureId(int id);

	@Query("select cl.course from CourseLecture cl where cl.course.lecturer.userAccount.id = :id")
	Course findCourseByLectureId(int id);

}
