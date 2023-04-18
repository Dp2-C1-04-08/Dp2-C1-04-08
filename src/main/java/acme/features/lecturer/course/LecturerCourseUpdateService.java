
package acme.features.lecturer.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseUpdateService extends AbstractService<Lecturer, Course> {

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
		boolean sameLecturer;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		courseId = super.getRequest().getData("id", int.class);
		course = this.repository.findOneCourseById(courseId);
		sameLecturer = course.getLecturer().getId() == lecturerId;

		status = super.getRequest().getPrincipal().hasRole(Lecturer.class) && sameLecturer;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Course course = this.repository.findOneCourseById(id);
		super.getBuffer().setData(course);
	}
	@Override
	public void bind(final Course object) {
		assert object != null;
		super.bind(object, "code", "title", "courseAbstract", "courseType", "retailPrice", "link");

	}
	@Override
	public void validate(final Course object) {
		assert object != null;

		boolean draft;
		boolean isDraft;

		draft = object.isDraft();
		isDraft = draft;

		super.state(isDraft, "*", "lecturer.course.form.error.update.draft");

	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		boolean draft;

		draft = super.getRequest().getData("draft", boolean.class);

		object.setDraft(draft);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {

		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "courseAbstract", "courseType", "retailPrice", "link");

		super.getResponse().setData(tuple);
	}

}
