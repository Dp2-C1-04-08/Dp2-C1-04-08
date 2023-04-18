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

import acme.entities.courses.Nature;
import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class ActivityShowService extends AbstractService<Student, Activity> {

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
		int masterId;
		Enrolment enrolment;

		masterId = super.getRequest().getData("masterId", int.class);
		enrolment = this.repository.findEnrolmentById(masterId);
		status = enrolment != null && super.getRequest().getPrincipal().hasRole(enrolment.getStudent());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Activity object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findActivityById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		final SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(Nature.class, object.getActivityType());

		tuple = super.unbind(object, "title", "activityAbstract", "activityType", "startTime", "endTime", "link");

		tuple.put("activityTypes", choices);
		super.getResponse().setData(tuple);
	}

}
