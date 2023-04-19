/*
 * EmployerDutyCreateService.java
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
import java.util.Date;

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
public class AssistantSessionTutorialCreateService extends AbstractService<Assistant, SessionTutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantSessionTutorialRepository repository;

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

		Principal principal;

		principal = super.getRequest().getPrincipal();
		masterId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorialById(masterId);

		status = tutorial != null && tutorial.isDraft() && tutorial.getAssistant().getId() == principal.getActiveRoleId();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SessionTutorial object;
		int masterId;
		Tutorial tutorial;

		masterId = super.getRequest().getData("masterId", int.class);
		tutorial = this.repository.findOneTutorialById(masterId);
		object = new SessionTutorial();
		object.setTutorial(tutorial);
		object.setDraft(true);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final SessionTutorial object) {
		assert object != null;

		super.bind(object, "title", "abstractStr", "link", "type", "startTime", "endTime");
	}

	@Override
	public void validate(final SessionTutorial object) {
		Date moment;
		moment = MomentHelper.getCurrentMoment();
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = super.getRequest().getData("startTime", Date.class);
		} catch (final Exception e) {
		}
		try {
			endDate = super.getRequest().getData("endTime", Date.class);
		} catch (final Exception e) {
		}
		if (startDate != null) {
			final Duration startDelay = MomentHelper.computeDuration(moment, startDate);
			final Duration startMinimumDuration = Duration.ofDays(1);
			final Boolean startDelayBool = startMinimumDuration.minus(startDelay).isNegative();
			super.state(startDelayBool, "startTime", "assistant.sessionTutorial.form.error.start-date.invalid");

			if (endDate != null) {
				final Duration duration = MomentHelper.computeDuration(startDate, endDate);
				final Duration minimumDuration = Duration.ofHours(1);
				final Duration maximumDuration = Duration.ofHours(5);
				final Boolean minimumDelayBool = duration.minus(minimumDuration).isNegative();
				final Boolean maximumDelayBool = maximumDuration.minus(duration).isNegative();

				super.state(!minimumDelayBool, "endTime", "assistant.sessionTutorial.form.error.end-date.too-short");
				super.state(!maximumDelayBool, "endTime", "assistant.sessionTutorial.form.error.end-date.too-long");
			}
		}
	}

	@Override
	public void perform(final SessionTutorial object) {
		assert object != null;

		// compute the new estimatedTotalDuration of the tutorial
		final Tutorial tutorial = object.getTutorial();
		Double totalDurationInHours = 0.;
		final Collection<SessionTutorial> sessions = this.repository.findManySessionsTutorialByMasterId(tutorial.getId());
		for (final SessionTutorial st : sessions)
			if (st.getId() != object.getId()) {
				final Duration stDuration = MomentHelper.computeDuration(st.getStartTime(), st.getEndTime());
				final double stHours = stDuration.getSeconds() / 3600.;
				totalDurationInHours += stHours;
			}
		final Duration objectDuration = MomentHelper.computeDuration(object.getStartTime(), object.getEndTime());
		final double objectHours = objectDuration.getSeconds() / 3600.;
		totalDurationInHours += objectHours;

		final double randomMultiplier = Math.random() * 0.2 - 0.1;
		totalDurationInHours = totalDurationInHours * (1 + randomMultiplier);
		tutorial.setEstimatedTotalTime(totalDurationInHours);

		this.repository.save(object);
		this.repository.save(tutorial);
	}

	@Override
	public void unbind(final SessionTutorial object) {
		assert object != null;
		SelectChoices choices;
		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractStr", "link", "startTime", "endTime", "draft");
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));
		tuple.put("draftMode", object.getTutorial().isDraft());
		choices = SelectChoices.from(Nature.class, object.getType());
		tuple.put("types", choices);
		tuple.put("type", choices.getSelected());

		super.getResponse().setData(tuple);
	}

}
