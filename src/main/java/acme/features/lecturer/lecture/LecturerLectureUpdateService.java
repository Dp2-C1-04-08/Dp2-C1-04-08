
package acme.features.lecturer.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.courses.CourseLecture;
import acme.entities.courses.Lecture;
import acme.features.lecturer.courseLecture.LecturerCourseLectureRepository;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureUpdateService extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureRepository	repository;

	@Autowired
	LecturerCourseLectureRepository		lecturerCourseLectureRepository;


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
		boolean sameLecturer;
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
		final Lecture lecture = this.repository.findOneLectureById(id);

		super.getBuffer().setData(lecture);
	}
	@Override
	public void bind(final Lecture object) {
		assert object != null;

		super.bind(object, "title", "lectureAbstract", "link", "estimatedLearningTime", "body", "lectureType", "draft");

	}
	@Override
	public void validate(final Lecture object) {
		assert object != null;

		boolean isDraft;
		CourseLecture courseLecture;

		courseLecture = this.repository.findCourseLectureByLectureId(object.getId());
		isDraft = courseLecture.getCourse().isDraft();
		super.state(isDraft, "title", "lecturer.lecture.form.error.isDraft.update");

	}
	@Override
	public void perform(final Lecture object) {
		assert object != null;

		int masterId;
		Course course;
		CourseLecture courseLecture;

		courseLecture = new CourseLecture();
		masterId = super.getRequest().getData("masterId", int.class);
		course = this.repository.findCourseById(masterId);

		courseLecture.setCourse(course);
		courseLecture.setLecture(object);

		this.repository.save(object);
		this.lecturerCourseLectureRepository.save(courseLecture);
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "lectureAbstract", "link", "estimatedLearningTime", "body", "lectureType", "draft");
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));

		super.getResponse().setData(tuple);
	}

}
