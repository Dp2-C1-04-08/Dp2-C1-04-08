
package acme.features.lecturer.course;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.MoneyService;
import acme.entities.courses.Course;
import acme.framework.components.datatypes.Money;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseUpdateService extends AbstractService<Lecturer, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository	repository;

	@Autowired
	MoneyService						moneyService;

	// AbstractService interface ----------------------------------------------


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
		final int id = super.getRequest().getData("id", int.class);
		final Course course = this.repository.findOneCourseById(id);
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

		String code;
		Optional<Course> courseWhithSameCode;
		Money price = null;

		code = super.getRequest().getData("code", String.class);
		courseWhithSameCode = this.repository.findOneCourseByCode(code);

		super.state(!courseWhithSameCode.isPresent() || object.getId() == courseWhithSameCode.get().getId(), "code", "lecturer.course.form.error.update.code.duplicated");
		try {
			price = super.getRequest().getData("retailPrice", Money.class);
			super.state(this.moneyService.checkContains(price.getCurrency()), "retailPrice", "lecturer.course.form.error.price.invalid-currency");
			super.state(price.getAmount() > 0.0, "retailPrice", "lecturer.course.form.error.retailPrice.negative-or-zero");
			super.state(price.getAmount() < 1000000, "retailPrice", "lecturer.course.form.error.retailPrice.too-big");
		} catch (final Exception e) {
		}
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

		tuple = super.unbind(object, "code", "title", "courseAbstract", "courseType", "retailPrice", "link", "draft");

		super.getResponse().setData(tuple);
	}

}
