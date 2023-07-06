
package acme.features.lecturer.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.courses.Lecture;
import acme.entities.courses.Nature;
import acme.forms.LecturerDashboard;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerDashboardShowService extends AbstractService<Lecturer, LecturerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerDashboardRepository repository;

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
		LecturerDashboard dashboard;
		final HashMap<Nature, Integer> totalLecture = new HashMap<Nature, Integer>();
		totalLecture.put(Nature.THEORETICAL, 0);
		totalLecture.put(Nature.HANDS_ON, 0);
		Collection<Course> courses;
		Collection<Lecture> lectures;
		Principal principal;
		principal = super.getRequest().getPrincipal();
		final int lecturerId = principal.getActiveRoleId();
		courses = this.repository.findCoursesByLecturerId(lecturerId);
		lectures = this.repository.findLecturesByLecturerId(lecturerId);

		for (final Lecture l : lectures) {
			final Nature type = l.getLectureType();
			Integer count = totalLecture.getOrDefault(type, 0);
			count += 1;
			totalLecture.put(type, count);
		}
		Double averageLecture;
		Double deviationLecture;
		Double minimumLecture;
		Double maximumLecture;
		Double averageCourse;
		Double deviationCourse;
		Double minimumCourse;
		Double maximumCourse;

		if (lectures.isEmpty()) {
			averageLecture = 0.;
			deviationLecture = 0.;
			minimumLecture = 0.;
			maximumLecture = 0.;
		} else {
			averageLecture = this.repository.averageLecture(lecturerId);
			deviationLecture = this.repository.deviationLecture(lecturerId);
			minimumLecture = this.repository.minLecture(lecturerId);
			maximumLecture = this.repository.maxLecture(lecturerId);
		}

		if (courses.isEmpty()) {
			averageCourse = 0.;
			deviationCourse = 0.;
			minimumCourse = 0.;
			maximumCourse = 0.;
		} else {
			List<Double> courseDurations;
			courseDurations = new ArrayList<>();
			for (final Course course : courses) {
				Double sumLectureForCourse;
				sumLectureForCourse = this.repository.sumLectureForCourseById(course.getId());
				if (sumLectureForCourse == null)
					sumLectureForCourse = 0.;
				courseDurations.add(sumLectureForCourse);
			}
			averageCourse = courseDurations.stream().mapToDouble(d -> d).average().getAsDouble();
			minimumCourse = courseDurations.stream().mapToDouble(d -> d).min().getAsDouble();
			maximumCourse = courseDurations.stream().mapToDouble(d -> d).max().getAsDouble();
			final double sumOfSquaredDifferences = courseDurations.stream().mapToDouble(d -> d).map(datum -> Math.pow(datum - averageCourse, 2)).sum();
			deviationCourse = Math.sqrt(sumOfSquaredDifferences / courseDurations.size());
		}

		dashboard = new LecturerDashboard();
		dashboard.setTotalLecture(totalLecture);

		dashboard.setAverageLecture(averageLecture);
		dashboard.setDeviationLecture(deviationLecture);
		dashboard.setMinimumLecture(minimumLecture);
		dashboard.setMaximumLecture(maximumLecture);

		dashboard.setAverageCourse(averageCourse);
		dashboard.setDeviationCourse(deviationCourse);
		dashboard.setMinimumCourse(minimumCourse);
		dashboard.setMaximumCourse(maximumCourse);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final LecturerDashboard object) {
		Tuple tuple = null;

		tuple = super.unbind(object,  //
			"averageLecture", "deviationLecture", "minimumLecture", "maximumLecture", "averageCourse", "deviationCourse", "minimumCourse", "maximumCourse");

		tuple.put("handsOnLectureCount", object.getTotalLecture().get(Nature.HANDS_ON));
		tuple.put("theoreticalLectureCount", object.getTotalLecture().get(Nature.THEORETICAL));
		super.getResponse().setData(tuple);
	}

}
