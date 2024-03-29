
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Lecture;
import acme.entities.courses.Nature;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureShowService extends AbstractService<Lecturer, Lecture> {

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
		boolean status;
		int lectureId;
		int lecturerId;
		Lecture lecture;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();

		lectureId = super.getRequest().getData("id", int.class);

		lecture = this.repository.findOneLectureById(lectureId);

		status = lecture.getLecturer().getId() == lecturerId;

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
	public void unbind(final Lecture object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		tuple = super.unbind(object, "title", "lectureAbstract", "estimatedLearningTime", "body", "link", "draft");
		choices = SelectChoices.from(Nature.class, object.getLectureType());
		tuple.put("lectureTypes", choices);
		tuple.put("lectureType", choices.getSelected());

		super.getResponse().setData(tuple);
	}

}
