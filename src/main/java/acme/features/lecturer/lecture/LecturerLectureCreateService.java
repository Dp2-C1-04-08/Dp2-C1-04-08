
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
public class LecturerLectureCreateService extends AbstractService<Lecturer, Lecture> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository	repository;

	@Autowired
	LecturerCourseLectureRepository		lecturerCourseLectureRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		Boolean status;

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
		course = this.repository.findCourseById(masterId);

		status = course.getLecturer().getId() == lecturerId;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Lecture lecture;
		int masterId;
		Course course;
		CourseLecture courseLecture;

		lecture = new Lecture();
		lecture.setDraft(true);
		courseLecture = new CourseLecture();
		masterId = super.getRequest().getData("masterId", int.class);
		course = this.repository.findCourseById(masterId);
		courseLecture.setCourse(course);

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
	}

	@Override
	public void perform(final Lecture object) {
		assert object != null;
		CourseLecture courseLecture;

		courseLecture = this.repository.findCourseLectureByLectureId(object.getId());

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
