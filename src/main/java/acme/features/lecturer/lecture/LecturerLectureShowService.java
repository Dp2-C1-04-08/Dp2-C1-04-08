
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.courses.Lecture;
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
		int id;
		Lecturer lecturer;

		id = super.getRequest().getData("id", int.class);
		lecturer = this.repository.findLecturerByLectureId(id);
		status = lecturer.getUserAccount().getId() == super.getRequest().getPrincipal().getAccountId();

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

		Tuple tuple;
		Course course;

		course = this.repository.findCourseByLectureId(object.getId());

		tuple = super.unbind(object, "title", "lectureAbstract", "estimatedLearningTime", "body", "lectureType", "link");
		tuple.put("masterId", course.getId());

		super.getResponse().setData(tuple);
	}

}
