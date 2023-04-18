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

package acme.features.assistant.sessionTutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Nature;
import acme.entities.tutorials.SessionTutorial;
import acme.entities.tutorials.Tutorial;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionTutorialShowService extends AbstractService<Assistant, SessionTutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionTutorialRepository repository;

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

		Principal principal;

		principal = super.getRequest().getPrincipal();

		sessionTutorialId = super.getRequest().getData("id", int.class);
		tutorial = this.repository.findOneTutorialBySessionTutorialId(sessionTutorialId);

		status = tutorial != null && tutorial.getAssistant().getId() == principal.getActiveRoleId();

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
		SelectChoices choices;

		tuple = super.unbind(object, "title", "abstractStr", "type", "link", "startTime", "endTime");
		tuple.put("masterId", object.getTutorial().getId());
		tuple.put("draftMode", object.getTutorial().isDraft());

		choices = SelectChoices.from(Nature.class, object.getType());
		tuple.put("types", choices);
		tuple.put("type", choices.getSelected());
		super.getResponse().setData(tuple);
	}

}
