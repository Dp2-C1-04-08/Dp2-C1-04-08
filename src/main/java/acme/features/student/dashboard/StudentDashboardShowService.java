
package acme.features.student.dashboard;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.courses.Nature;
import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.forms.StudentDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Controller
public class StudentDashboardShowService extends AbstractService<Student, StudentDashboard> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@SuppressWarnings("null")
	@Override
	public void load() {
		final StudentDashboard dashboard;
		Integer theoreticalAct = 0;
		Integer handsOnAct = 0;
		final Double avgWorkTime;
		final Double deviationWorkTime;
		final Double minWorkTime;
		final Double maxWorkTime;

		final double avgPeriod;
		final Double deviationPeriod;
		final Double minPeriod;
		final Double maxPeriod;

		Integer studentId;
		studentId = super.getRequest().getPrincipal().getActiveRoleId();

		final Collection<Activity> theoretical = null;
		final Collection<Activity> handsOn = null;
		final Collection<Activity> activities = this.repository.findActivitiesByStudentId(studentId);
		final Collection<Enrolment> enrolments = this.repository.findEnrolmentsByStudentId(studentId);

		for (final Activity a : activities)
			if (a.getActivityType() == Nature.THEORETICAL)
				theoreticalAct = theoreticalAct + 1;
			else if (a.getActivityType() == Nature.HANDS_ON)
				handsOnAct = handsOnAct + 1;

		final Collection<Double> workTimes = null;

		for (final Enrolment e : enrolments)
			workTimes.add(e.getWorkTime());

		if (!workTimes.isEmpty())
			avgWorkTime = workTimes.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
		else
			avgWorkTime = 0.0;

		final double aux = workTimes.stream().mapToDouble(Double::doubleValue).map(x -> Math.pow(x - avgWorkTime, 2)).sum();
		deviationWorkTime = Math.sqrt(aux / workTimes.size());
		minWorkTime = Collections.min(workTimes);
		maxWorkTime = Collections.max(workTimes);

		//		theoreticalAct = theoretical.size();
		//		handsOnAct = handsOn.size();
		//		avgWorkTime = this.repository.averageWorkTimeStudent(studentId);
		//		deviationWorkTime = this.repository.deviationWorkTimeStudent(studentId);
		//		minWorkTime = this.repository.minWorkTimeStudent(studentId);
		//		maxWorkTime = this.repository.maxWorkTimeStudent(studentId);

		final Collection<Double> periods = null;

		for (final Activity a : activities) {
			final Date start = a.getStartTime();
			final Date end = a.getEndTime();
			final Duration time = MomentHelper.computeDuration(start, end);
			final long seconds = time.getSeconds();
			final double minutes = seconds / 60.0;
			final double hours = minutes / 60.0;
			periods.add(hours);
		}

		if (!periods.isEmpty())
			avgPeriod = periods.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
		else
			avgPeriod = 0.0;

		final double aux2 = periods.stream().mapToDouble(Double::doubleValue).map(x -> Math.pow(x - avgPeriod, 2)).sum();
		deviationPeriod = Math.sqrt(aux2 / periods.size());
		minPeriod = Collections.min(periods);
		maxPeriod = Collections.max(periods);

		dashboard = new StudentDashboard();
		dashboard.setNumberoOfTheoryActivities(theoreticalAct);
		dashboard.setNumberOfHandsOnActivities(handsOnAct);
		dashboard.setAveragePeriodOfActivities(avgPeriod);
		dashboard.setDeviationPeriodOfActivities(deviationPeriod);
		dashboard.setMinimumPeriodOfActivities(minPeriod);
		dashboard.setMaximumPeriodOfActivities(maxPeriod);
		dashboard.setAverageLearningTime(avgWorkTime);
		dashboard.setDeviationLearningTime(deviationWorkTime);
		dashboard.setMinimumLearningTime(minWorkTime);
		dashboard.setMaximumLearningTime(maxWorkTime);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final StudentDashboard object) {
		Tuple tuple;

		tuple = super.unbind(object, //
			"numberOfTheoreticalActivities", "numberOfHandsOnActivities", // 
			"minimumPeriodOfActivities", "maximumPeriodOfActivities", "averagePeriodOfActivities", "deviationPeriodOfActivities", "minimumLearningTime", "maximumLearningTime", "averageLearningTime", "deviationLearningTime");

		super.getResponse().setData(tuple);
	}

}
