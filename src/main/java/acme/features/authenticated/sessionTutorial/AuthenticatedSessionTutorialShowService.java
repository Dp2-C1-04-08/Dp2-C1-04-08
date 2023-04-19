/*
 * EmployerDutyShowService.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorials.SessionTutorial;
import acme.entities.tutorials.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedSessionTutorialShowService extends AbstractService<Authenticated, SessionTutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedSessionTutorialRepository repository;

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
		int sessionTutorialId;
		Tutorial tutorial;
		SessionTutorial sessionTutorial;
		sessionTutorialId = super.getRequest().getData("id", int.class);
		sessionTutorial = this.repository.findOneSessionTutorialById(sessionTutorialId);
		tutorial = sessionTutorial.getTutorial();

		status = tutorial != null && !sessionTutorial.isDraft() && !tutorial.isDraft() && !tutorial.getCourse().isDraft();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SessionTutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSessionTutorialById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final SessionTutorial object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractStr", "type", "link", "startTime", "endTime");
		tuple.put("draftMode", object.getTutorial().isDraft());
		tuple.put("type", object.getType());
		super.getResponse().setData(tuple);
	}

}
