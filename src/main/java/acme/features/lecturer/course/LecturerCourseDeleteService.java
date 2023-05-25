
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.courses.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseDeleteService extends AbstractService<Lecturer, Course> {

	@Autowired
	protected LecturerCourseRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		int lecturerId;
		int courseId;
		Course course;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		courseId = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(courseId);
		status = course.getLecturer().getId() == lecturerId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id;
		Course course;

		id = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(id);

		super.getBuffer().setData(course);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;
		super.bind(object, "code", "title", "courseAbstract", "courseType", "retailPrice", "link", "draft");

	}

	@Override
	public void validate(final Course object) {
		assert object != null;

		boolean isDraft;
		final boolean haveLecture;
		Collection<Lecture> lectures;

		lectures = this.repository.haveLecturesById(object.getId());
		haveLecture = lectures.isEmpty();
		isDraft = object.isDraft();

		super.state(isDraft, "*", "lecturer.course.form.error.delete.draft");
		super.state(haveLecture, "*", "lecturer.course.form.error.delete.lectures");
	}
	@Override
	public void perform(final Course object) {
		assert object != null;

		this.repository.delete(object);
	}
	@Override
	public void unbind(final Course object) {

		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "courseAbstract", "courseType", "retailPrice", "link", "lecturer", "draft");

		super.getResponse().setData(tuple);

	}
}
