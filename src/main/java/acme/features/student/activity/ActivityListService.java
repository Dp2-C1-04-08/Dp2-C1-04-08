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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class ActivityListService extends AbstractService<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ActivityRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
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
		Collection<Activity> objects;
		int id;

		id = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findActivitiesByEnrolmentId(id);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Activity object) {
		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "title", "activityAbstract", "activityType", "startTime", "endTime", "link");

		tuple.put("masterId", 4);
		tuple.put("finalised", object.getEnrolment().getIsFinalised());

		super.getResponse().setData(tuple);
	}
	@Override
	public void unbind(final Collection<Activity> objects) {
		assert objects != null;

		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		super.getResponse().setGlobal("masterId", masterId);
		boolean finalised = true;
		for (final Activity a : objects)
			finalised = finalised && a.getEnrolment().getIsFinalised();
		super.getResponse().setGlobal("finalised", finalised);
	}

}
