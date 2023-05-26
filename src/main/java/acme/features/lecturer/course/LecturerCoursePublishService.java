
package acme.features.lecturer.course;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.courses.Lecture;
import acme.entities.courses.Nature;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCoursePublishService extends AbstractService<Lecturer, Course> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Collection<Course> objects;
		Principal principal;
		Collection<Integer> objectsIds;
		Course object;
		int id;
		Boolean status;

		principal = super.getRequest().getPrincipal();
		objects = this.repository.findManyCoursesByLecturerId(principal.getActiveRoleId());

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);
		objectsIds = objects.stream().map(c -> c.getId()).collect(Collectors.toList());
		status = object.isDraft() && objectsIds.contains(id);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Course object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Course object) {
		assert object != null;
		super.bind(object, "code", "title", "courseAbstract", "courseType", "retailPrice", "link", "draft");
	}

	@Override
	public void validate(final Course object) {

		boolean isDraft;
		boolean handsOnExist;
		Collection<Lecture> lectures;
		boolean acumulador;

		acumulador = false;
		handsOnExist = false;
		isDraft = object.isDraft();

		lectures = this.repository.findLecturesByCourseId(object.getId());

		for (final Lecture lecture : lectures)
			acumulador = acumulador || lecture.isDraft();

		super.state(isDraft, "*", "lecturer.course.form.error.update.draft");
		super.state(!acumulador, "*", "lecuter.course.form.error.update.publish");
		super.state(!lectures.isEmpty(), "*", "lecturer.course.form.error.general.publish.no-lecture");
		for (final Lecture lecture : lectures)
			if (lecture.getLectureType().equals(Nature.HANDS_ON))
				handsOnExist = true;
		super.state(handsOnExist, "*", "lecturer.course.form.error.general.publish.no-handsOn");
	}

	@Override
	public void perform(final Course object) {
		assert object != null;
		object.setDraft(false);

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
