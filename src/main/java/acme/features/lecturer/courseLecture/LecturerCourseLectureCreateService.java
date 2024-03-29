
package acme.features.lecturer.courseLecture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;

import acme.entities.courses.Course;
import acme.entities.courses.CourseLecture;
import acme.entities.courses.Lecture;
import acme.entities.courses.Nature;
import acme.features.lecturer.lecture.LecturerLectureRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseLectureCreateService extends AbstractService<Lecturer, CourseLecture> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseLectureRepository	repository;

	@Autowired
	LecturerLectureRepository					lrepository;

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
		CourseLecture courselecture;
		Lecturer lecturer;
		int lecturerId;

		courselecture = new CourseLecture();
		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		lecturer = this.repository.findLecturerById(lecturerId);
		courselecture.setLecturer(lecturer);
		super.getBuffer().setData(courselecture);
	}

	@Override
	public void bind(final CourseLecture object) {
		assert object != null;
		int courseId;
		int lectureId;
		Course course;
		Lecture lecture;

		Lecturer lecturer;
		int lecturerId;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		lecturer = this.repository.findLecturerById(lecturerId);
		object.setLecturer(lecturer);

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);
		object.setCourse(course);

		lectureId = super.getRequest().getData("lecture", int.class);
		lecture = this.repository.findLectureById(lectureId);
		object.setLecture(lecture);

	}

	@Override
	public void validate(final CourseLecture object) {
		Course course;
		Lecture lecture;
		int lecturerId;
		boolean isCourseLecturer;
		boolean isLectureLecturer;
		Optional<CourseLecture> courseLecture;
		boolean alredyExist;
		boolean courseIsDraft;

		course = object.getCourse();
		lecture = object.getLecture();
		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();

		isCourseLecturer = course.getLecturer().getId() == lecturerId;
		isLectureLecturer = lecture.getLecturer().getId() == lecturerId;
		courseIsDraft = course.isDraft();

		courseLecture = this.repository.findCourseLectureByLectureAndCourseId(course.getId(), lecture.getId());
		alredyExist = courseLecture.isPresent();

		super.state(isCourseLecturer, "course", "lecturer.courselecture.form.error.course.invalid-lecturer");
		super.state(isLectureLecturer, "lecture", "lecturer.courselecture.form.error.lecture.invalid-lecturer");
		super.state(!alredyExist, "*", "lecturer.courselecture.form.error.alredyExist");
		super.state(courseIsDraft, "course", "lecturer.courselecture.form.error.courseNotIsDraft");
	}

	@Override
	public void perform(final CourseLecture object) {
		assert object != null;
		int courseId;
		int lectureId;
		Course course;
		Lecture lecture;

		Lecturer lecturer;
		int lecturerId;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		lecturer = this.repository.findLecturerById(lecturerId);
		object.setLecturer(lecturer);

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findCourseById(courseId);
		object.setCourse(course);

		lectureId = super.getRequest().getData("lecture", int.class);
		lecture = this.repository.findLectureById(lectureId);
		object.setLecture(lecture);

		this.repository.save(object);

		int index = 0;
		List<Nature> types;

		final Collection<javax.persistence.Tuple> col = this.lrepository.countLecturesGroupByType(course.getId());

		long max = 0;
		types = new ArrayList<>();
		for (final javax.persistence.Tuple t : col)
			if ((long) t.get(1) == max)
				types.add((Nature) t.get(0));
			else if ((long) t.get(1) > max) {
				max = (long) t.get(1);
				types.clear();
				types.add((Nature) t.get(0));
			}

		ThreadLocalRandom random;
		random = ThreadLocalRandom.current();
		index = random.nextInt(0, types.size());
		course.setCourseType(types.get(index));

		this.repository.save(course);
	}

	@Override
	public void unbind(final CourseLecture object) {
		assert object != null;

		Tuple tuple;
		Collection<Course> courses;
		Collection<Lecture> lectures;
		SelectChoices choicesCourse;
		SelectChoices choicesLecture;
		Integer lecturerId;

		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();
		courses = this.repository.findCoursesByLecturerId(lecturerId);
		lectures = this.repository.findLecturesByLecturerId(lecturerId);
		choicesCourse = SelectChoices.from(courses, "title", object.getCourse());
		choicesLecture = SelectChoices.from(lectures, "title", object.getLecture());

		tuple = super.unbind(object, "course", "lecture");

		tuple.put("courses", choicesCourse);
		tuple.put("course", choicesCourse.getSelected().getKey());
		tuple.put("lectures", choicesLecture);
		tuple.put("lecture", choicesLecture.getSelected().getKey());

		super.getResponse().setData(tuple);
	}
}
