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

package acme.features.assistant.tutorial;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.tutorials.SessionTutorial;
import acme.entities.tutorials.Tutorial;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialDeleteService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Collection<Tutorial> objects;
		Principal principal;
		Collection<Integer> objectsIds;
		int id;
		Boolean status;

		principal = super.getRequest().getPrincipal();
		objects = this.repository.findManyTutorialsByAssistantId(principal.getActiveRoleId());

		id = super.getRequest().getData("id", int.class);
		objectsIds = objects.stream().map(t -> t.getId()).collect(Collectors.toList());
		status = objectsIds.contains(id);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Tutorial object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTutorialById(id);

		super.getBuffer().setData(object);

	}

	@Override
	public void bind(final Tutorial object) {
		assert object != null;
	}

	@Override
	public void validate(final Tutorial object) {
		assert object != null;
		super.state(object.isDraft(), "*", "assistant.tutorial.form.error.general.delete.not-draft");
	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;
		final Collection<SessionTutorial> children = this.repository.getChildrenForTutorial(object.getId());
		for (final SessionTutorial child : children)
			this.repository.delete(child);
		this.repository.delete(object);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;
		Collection<Course> courses;
		final SelectChoices choices;
		courses = this.repository.findValidCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());

		tuple = super.unbind(object, "code", "title", "abstractStr", "goals", "estimatedTotalTime", "draft");
		tuple.put("courses", choices);
		tuple.put("course", choices.getSelected().getKey());
		super.getResponse().setData(tuple);
	}

}
