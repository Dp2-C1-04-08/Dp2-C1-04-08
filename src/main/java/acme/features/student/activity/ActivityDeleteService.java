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

package acme.features.student.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolments.Activity;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class ActivityDeleteService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ActivityRepository repository;

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
		Activity activity;

		id = super.getRequest().getData("id", int.class);
		activity = this.repository.findActivityById(id);
		status = super.getRequest().getPrincipal().getActiveRoleId() == activity.getEnrolment().getStudent().getId() && activity.getEnrolment().getIsFinalised() && activity != null
			&& super.getRequest().getPrincipal().hasRole(activity.getEnrolment().getStudent());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Activity activity = this.repository.findActivityById(id);
		super.getBuffer().setData(activity);
	}

	@Override
	public void bind(final Activity object) {
		assert object != null;

		super.bind(object, "title", "activityAbstract", "activityType", "startTime", "endTime", "link");
	}

	@Override
	public void validate(final Activity object) {
		assert object != null;
	}

	@Override
	public void perform(final Activity object) {
		assert object != null;

		this.repository.delete(object);
	}
	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "activityAbstract", "activityType", "startTime", "endTime", "link");

		super.getResponse().setData(tuple);
	}

}
