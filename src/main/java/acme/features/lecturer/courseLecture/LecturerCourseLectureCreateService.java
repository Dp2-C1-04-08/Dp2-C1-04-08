
package acme.features.lecturer.courseLecture;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.courses.CourseLecture;
import acme.entities.courses.Lecture;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerCourseLectureCreateService extends AbstractService<Lecturer, CourseLecture> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerCourseLectureRepository repository;

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

		super.bind(object, "course", "lecture");
	}

	@Override
	public void validate(final CourseLecture object) {
		assert object != null;
	}

	@Override
	public void perform(final CourseLecture object) {
		assert object != null;

		this.repository.save(object);

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
		choicesCourse = SelectChoices.from(courses, "course", object.getCourse());
		choicesLecture = SelectChoices.from(lectures, "lecture", object.getLecture());

		tuple = super.unbind(object, "course", "lecture");
		tuple.put("courses", choicesCourse);
		tuple.put("course", choicesCourse.getSelected());
		tuple.put("lectures", choicesLecture);
		tuple.put("lecture", choicesLecture.getSelected());

		super.getResponse().setData(tuple);
	}
}
