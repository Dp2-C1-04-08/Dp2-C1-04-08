
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
import acme.framework.components.jsp.SelectChoices;
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
		Lecture lecture;

		lectureId = super.getRequest().getData("id", int.class);
		lecturerId = super.getRequest().getPrincipal().getActiveRoleId();

		lecture = this.repository.findOneLectureById(lectureId);

		status = lecture.getLecturer().getId() == lecturerId;

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
		Double estimatedLearningTime;

		try {
			estimatedLearningTime = super.getRequest().getData("estimatedLearningTime", Double.class);
			super.state(estimatedLearningTime > 0.0, "estimatedLearningTime", "lecturer.leccture.form.error.estimatedLearningTime.negative-or-zero");
		} catch (final Exception e) {
		}

	}
	@Override
	public void perform(final Lecture object) {
		assert object != null;

		try {
			CourseLecture courseLecture;
			Course course;
			int index;

			courseLecture = this.repository.findCourseLectureByLectureId(object.getId());
			course = courseLecture.getCourse();

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

			if (types.isEmpty())
				course.setCourseType(null);
			else
				course.setCourseType(types.get(index));

			this.repository.save(course);
		} catch (final Exception e) {
		}
	}

	@Override
	public void unbind(final Lecture object) {
		assert object != null;

		SelectChoices choices;
		Tuple tuple;

		tuple = super.unbind(object, "title", "lectureAbstract", "link", "estimatedLearningTime", "body", "lectureType", "draft");
		tuple.put("id", super.getRequest().getData("id", int.class));
		choices = SelectChoices.from(Nature.class, object.getLectureType());
		tuple.put("lectureTypes", choices);
		tuple.put("lectureType", choices.getSelected());

		super.getResponse().setData(tuple);
	}

}
