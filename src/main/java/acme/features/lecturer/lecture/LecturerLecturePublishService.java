
package acme.features.lecturer.lecture;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Lecture;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLecturePublishService extends AbstractService<Lecturer, Lecture> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Collection<Lecture> objects;
		Principal principal;
		Collection<Integer> objectsIds;
		Lecture object;
		int id;
		Boolean status;

		principal = super.getRequest().getPrincipal();
		objects = this.repository.findManyLecturesByLecturerId(principal.getActiveRoleId());

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneLectureById(id);
		objectsIds = objects.stream().map(c -> c.getId()).collect(Collectors.toList());
		status = object.isDraft() && objectsIds.contains(id);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Lecture object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneLectureById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Lecture object) {
		assert object != null;
		super.bind(object, "title", "lectureAbstract", "link", "estimatedLearningTime", "body", "lectureType", "draft");
	}

	@Override
	public void validate(final Lecture object) {

		boolean isDraft;

		isDraft = object.isDraft();

		super.state(isDraft, "*", "lecturer.lecture.form.error.isDraft.update");
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;
		object.setDraft(false);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "lectureAbstract", "link", "estimatedLearningTime", "body", "lectureType", "draft");

		super.getResponse().setData(tuple);
	}
}
