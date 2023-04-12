
package acme.features.lecturer.lecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Lecture> objects;
		int lecturerId;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findLecturesByLecturerId(lecturerId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;
		Lecturer lecturer;

		lecturer = this.repository.findLecturerByLectureId(object.getId());

		tuple = super.unbind(object, "title", "lectureAbstract", "estimatedLearningTime", "body", "lectureType", "link");
		tuple.put("lecturer", lecturer.getIdentity().getFullName());

		super.getResponse().setData(tuple);
	}

}
