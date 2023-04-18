/*
 * EmployerDutyUpdateService.java
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

import java.time.Duration;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Nature;
import acme.entities.tutorials.SessionTutorial;
import acme.entities.tutorials.Tutorial;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantSessionTutorialDeleteService extends AbstractService<Assistant, SessionTutorial> {

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
		int id;
		SessionTutorial sessionTutorial;

		Principal principal;

		principal = super.getRequest().getPrincipal();
		id = super.getRequest().getData("id", int.class);
		sessionTutorial = this.repository.findOneSessionTutorialById(id);

		status = sessionTutorial != null && sessionTutorial.getTutorial().isDraft() && sessionTutorial.isDraft() && sessionTutorial.getTutorial().getAssistant().getId() == principal.getActiveRoleId();
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
	public void bind(final SessionTutorial object) {
		assert object != null;
	}

	@Override
	public void validate(final SessionTutorial object) {
		super.state(object.isDraft(), "*", "assistant.sessionTutorial.form.error.general.delete.not-draft");
	}

	@Override
	public void perform(final SessionTutorial object) {

		// compute the new estimatedTotalDuration of the tutorial
		final Tutorial tutorial = object.getTutorial();
		double totalDurationInHours = 0.;
		final Collection<SessionTutorial> sessions = this.repository.findManySessionsTutorialByMasterId(tutorial.getId());
		for (final SessionTutorial st : sessions)
			if (st.getId() != object.getId()) {
				final Duration stDuration = MomentHelper.computeDuration(st.getStartTime(), st.getEndTime());
				final double stHours = stDuration.getSeconds() / 3600.;
				totalDurationInHours += stHours;
			}

		final double randomMultiplier = Math.random() * 0.2 - 0.1;
		totalDurationInHours = totalDurationInHours * (1 + randomMultiplier);
		tutorial.setEstimatedTotalTime(totalDurationInHours);

		this.repository.delete(object);
		this.repository.save(tutorial);

	}

	@Override
	public void unbind(final SessionTutorial object) {
		assert object != null;
		SelectChoices choices;
		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractStr", "link", "startTime", "endTime", "draft");
		choices = SelectChoices.from(Nature.class, object.getType());
		tuple.put("draftMode", object.getTutorial().isDraft());
		tuple.put("types", choices);
		tuple.put("type", choices.getSelected());

		super.getResponse().setData(tuple);
	}

}
