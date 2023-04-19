
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.CourseLecture;
import acme.entities.courses.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureDeleteService extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		boolean sameLecturer;
		int lectureId;
		int lecturerId;
		CourseLecture courseLecture;

		lectureId = super.getRequest().getData("id", int.class);
		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		courseLecture = this.repository.findCourseLectureByLectureId(lectureId);

		status = super.getRequest().getPrincipal().hasRole(Lecturer.class);
		sameLecturer = courseLecture.getCourse().getLecturer().getId() == lecturerId;
		status = status && sameLecturer;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		Lecture lecture;
		lecture = this.repository.findOneLectureById(id);
		super.getBuffer().setData(lecture);
	}
	@Override
	public void bind(final Lecture object) {
		assert object != null;
		super.bind(object, "title", "lectureAbstract", "link", "estimatedLearningTime", "body", "lectureType");

	}

	@Override
	public void validate(final Lecture object) {
		assert object != null;

		boolean isDraft;
		CourseLecture courseLecture;

		courseLecture = this.repository.findCourseLectureByLectureId(object.getId());

		isDraft = courseLecture.getCourse().isDraft();
		super.state(isDraft, "title", "lecturer.lecture.form.error.isDraft.delete");

	}
	@Override
	public void perform(final Lecture object) {
		assert object != null;

		this.repository.delete(object);
	}
	@Override
	public void unbind(final Lecture object) {

		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "title", "lectureAbstract", "link", "estimatedLearningTime", "body", "lectureType");

		super.getResponse().setData(tuple);

	}

}
