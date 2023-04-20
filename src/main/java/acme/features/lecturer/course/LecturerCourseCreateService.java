
package acme.features.lecturer.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

import acme.entities.courses.Course;
import acme.entities.courses.Nature;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseCreateService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Lecturer.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Course course;
		Lecturer lecturer;
		course = new Course();
		lecturer = this.repository.findLecturerById(super.getRequest().getPrincipal().getActiveRoleId());
		course.setLecturer(lecturer);
		course.setDraft(true);
		course.setCourseType(Nature.NOT_ASIGN);
		super.getBuffer().setData(course);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;

		super.bind(object, "code", "title", "courseAbstract", "retailPrice", "link");
	}

	@Override
	public void validate(final Course object) {
		String code;
		Optional<Course> courseWhithSameCode;

		code = super.getRequest().getData("code", String.class);
		courseWhithSameCode = this.repository.findOneCourseByCode(code);

		super.state(!courseWhithSameCode.isPresent(), "code", "lecturer.course.form.error.code.duplicated");

	}

	@Override
	public void perform(final Course object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "courseAbstract", "retailPrice", "link");

		super.getResponse().setData(tuple);
	}

}
