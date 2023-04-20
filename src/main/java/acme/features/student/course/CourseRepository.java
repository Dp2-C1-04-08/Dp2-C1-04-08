/*
 * AuthenticatedAnnouncementRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.student.course;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.courses.CourseLecture;
import acme.entities.courses.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CourseRepository extends AbstractRepository {

	@Query("SELECT c FROM Course c WHERE c.id = :id")
	Course findCourseById(int id);

	@Query("SELECT c FROM Course c WHERE c.draft= false")
	Collection<Course> findAllCourses();

	@Query("SELECT cl FROM CourseLecture cl WHERE cl.course.id = :id")
	Collection<CourseLecture> findCourseLecturesByCourseId(int id);

	@Query("SELECT cl.lecture FROM CourseLecture cl WHERE cl.course.id = :id")
	Collection<Lecture> findLecturesByCourseId(int id);
}
