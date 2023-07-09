
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
public class LecturerLectureDeleteService extends AbstractService<Lecturer, Lecture> {

	@Autowired
	protected LecturerLectureRepository	repository;

	@Autowired
	LecturerCourseLectureRepository		clrepository;


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

		isDraft = object.isDraft();
		super.state(isDraft, "title", "lecturer.lecture.form.error.isDraft.delete");

	}
	@Override
	public void perform(final Lecture object) {
		assert object != null;

		try {
			CourseLecture courseLecture;
			Course course;
			int index = 0;
			List<Nature> types;

			courseLecture = this.repository.findCourseLectureByLectureId(object.getId());
			course = courseLecture.getCourse();
			final Collection<javax.persistence.Tuple> col = this.repository.countLecturesGroupByType(course.getId());

			this.clrepository.delete(courseLecture);
			this.repository.delete(object);

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
		} catch (final Exception e) {
			this.repository.delete(object);
		}
	}
	@Override
	public void unbind(final Lecture object) {

		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "title", "lectureAbstract", "link", "estimatedLearningTime", "body", "lectureType");

		super.getResponse().setData(tuple);

	}

}
