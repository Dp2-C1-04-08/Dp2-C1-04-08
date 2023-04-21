
package acme.features.lecturer.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

import acme.components.MoneyService;
import acme.entities.courses.Course;
import acme.entities.courses.Lecture;
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
		String code;
		Course course;
		Optional<Course> courseWhithSameCode;
		Collection<Lecture> lectures;
		boolean acumulador;
		Money price = null;

		acumulador = false;
		draft = super.getRequest().getData("draft", boolean.class);
		course = this.repository.findOneCourseById(object.getId());
		isDraft = course.isDraft();

		lectures = this.repository.findLecturesByCourseId(course.getId());

		if (isDraft && !draft)
			for (final Lecture lecture : lectures)
				acumulador = acumulador || lecture.isDraft();

		code = super.getRequest().getData("code", String.class);
		courseWhithSameCode = this.repository.findOneCourseByCode(code);

		price = super.getRequest().getData("retailPrice", Money.class);

		super.state(!courseWhithSameCode.isPresent() || object.getId() == courseWhithSameCode.get().getId(), "code", "lecturer.course.form.error.update.code.duplicated");
		super.state(isDraft, "*", "lecturer.course.form.error.update.draft");
		super.state(!acumulador, "*", "lecuter.course.form.error.update.publish");
		super.state(this.moneyService.checkContains(price.getCurrency()), "retailPrice", "lecturer.course.form.error.price.invalid-currency");
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
