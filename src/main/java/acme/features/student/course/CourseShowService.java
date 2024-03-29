/*
 * AuthenticatedAnnouncementShowService.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.courses.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;
import acme.roles.Student;

@Service
public class CourseShowService extends AbstractService<Student, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CourseRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Course course;
		int id;

		id = super.getRequest().getData("id", int.class);
		course = this.repository.findCourseById(id);

		super.getBuffer().setData(course);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;
		int id;
		final Collection<Lecture> lectures;
		final Collection<String> lectureTitles;
		final String name;
		final String surname;
		final String email;
		Lecturer lecturer;

		Tuple tuple;
		id = super.getRequest().getData("id", int.class);

		tuple = super.unbind(object, "code", "title", "courseAbstract", "courseType", "retailPrice", "link");
		lectures = this.repository.findLecturesByCourseId(id);

		lecturer = object.getLecturer();
		name = lecturer.getUserAccount().getIdentity().getName();
		surname = lecturer.getUserAccount().getIdentity().getSurname();
		email = lecturer.getUserAccount().getIdentity().getEmail();
		lectureTitles = this.repository.findLectureTitlesByCourseId(id);

		tuple.put("lecturerFullName", name + " " + surname);
		tuple.put("lecturerEmail", email);
		tuple.put("lectures", lectureTitles);
		super.getResponse().setData(tuple);
	}

}
