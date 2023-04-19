/*
 * EmployertDutyListService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.sessionTutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorials.SessionTutorial;
import acme.entities.tutorials.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedSessionTutorialListService extends AbstractService<Authenticated, SessionTutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedSessionTutorialRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("masterId", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Tutorial tutorial;

		masterId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorialById(masterId);
		status = tutorial != null && !tutorial.isDraft() && !tutorial.getCourse().isDraft();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<SessionTutorial> objects;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findManySessionsTutorialByMasterId(masterId);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final SessionTutorial object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractStr", "type", "startTime");

		super.getResponse().setData(tuple);
	}

	@Override
	public void unbind(final Collection<SessionTutorial> objects) {
		assert objects != null;
	}

}
