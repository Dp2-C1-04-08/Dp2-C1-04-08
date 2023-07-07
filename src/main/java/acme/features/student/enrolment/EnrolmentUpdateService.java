/*
 * AuthenticatedAnnouncementShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.student.enrolment;

import java.time.Duration;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class EnrolmentUpdateService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected EnrolmentRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Enrolment enrolment;
		int student;

		id = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(id);
		student = super.getRequest().getPrincipal().getActiveRoleId();
		status = student == enrolment.getStudent().getId() && !enrolment.getIsFinalised();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Enrolment object = this.repository.findEnrolmentById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;
		super.bind(object, "code", "motivation", "goals");
	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;
	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;
		final int id;

		Tuple tuple;

		tuple = super.unbind(object, "code", "motivation", "goals", "isFinalised", "course");

		id = super.getRequest().getData("id", int.class);
		final Collection<Activity> l = this.repository.findActivitiesByEnrolmentId(id);
		double worktime = 0.0;

		for (final Activity a : l) {
			final Date start = a.getStartTime();
			final Date end = a.getEndTime();
			final Duration time = MomentHelper.computeDuration(start, end);
			final long seconds = time.getSeconds();
			final double minutes = seconds / 60;
			final double hours = minutes / 60;
			worktime = worktime + hours;
		}
		tuple.put("worktime", worktime);
		tuple.put("courseCode", object.getCourse().getCode());
		tuple.put("courseTitle", object.getCourse().getTitle());
		super.getResponse().setData(tuple);
	}

}
