
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.courses.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureListService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId");
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		int lecturerId;
		Course course;

		masterId = super.getRequest().getData("masterId", int.class);
		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		super.getResponse().setGlobal("masterId", masterId);
		course = this.repository.findCourseById(masterId);
		status = course.getLecturer().getId() == lecturerId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Lecture> objects;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);

		objects = this.repository.findLecturesByCourseId(masterId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);

		tuple = super.unbind(object, "title", "lectureAbstract", "estimatedLearningTime", "body", "lectureType", "link");
		tuple.put("masterId", masterId);

		super.getResponse().setData(tuple);
	}

}
