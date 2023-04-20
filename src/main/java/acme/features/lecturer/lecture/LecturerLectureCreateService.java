
package acme.features.lecturer.lecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.courses.CourseLecture;
import acme.entities.courses.Lecture;
import acme.entities.courses.Nature;
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

		lecture = new Lecture();
		lecture.setDraft(true);

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
		int masterId;
		Course course;
		int index;

		masterId = super.getRequest().getData("masterId", int.class);
		course = this.repository.findCourseById(masterId);
		courseLecture = new CourseLecture();

		courseLecture.setCourse(course);
		courseLecture.setLecture(object);

		this.repository.save(object);
		this.lecturerCourseLectureRepository.save(courseLecture);

		long max = 0;
		final List<Nature> types = new ArrayList<>();
		final Collection<javax.persistence.Tuple> col = this.repository.countLecturesGroupByType(course.getId());
		for (final javax.persistence.Tuple t : col)
			if ((long) t.get(1) >= max) {
				types.add((Nature) t.get(0));
				max = (long) t.get(1);
			}
		ThreadLocalRandom random;
		random = ThreadLocalRandom.current();
		index = random.nextInt(0, types.size());

		course.setCourseType(types.get(index));

		this.repository.save(course);

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
