
package acme.features.lecturer.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.courses.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerDashboardRepository extends AbstractRepository {

	@Query("select avg(l.estimatedLearningTime) from Lecture l where l.lecturer.id = :id")
	Double averageLecture(int id);

	@Query("select STDDEV(l.estimatedLearningTime) from Lecture l where l.lecturer.id = :id")
	Double deviationLecture(int id);

	@Query("select min(l.estimatedLearningTime)from Lecture l where l.lecturer.id = :id")
	Double minLecture(int id);

	@Query("select max(l.estimatedLearningTime) from Lecture l where l.lecturer.id = :id")
	Double maxLecture(int id);

	@Query("select c from Course c where c.lecturer.id = :id")
	Collection<Course> findCoursesByLecturerId(int id);

	@Query("select l from Lecture l where l.lecturer.id = :id")
	Collection<Lecture> findLecturesByLecturerId(int id);

	@Query("select sum(cl.lecture.estimatedLearningTime) from CourseLecture cl where cl.course.id = :id")
	Double sumLectureForCourseById(int id);
}
