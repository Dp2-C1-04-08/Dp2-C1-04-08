/*
 * AdministratorAnnouncementCreateService.java
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
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.tutorials.Tutorial;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Assistant;

@Service
public class AssistantTutorialCreateService extends AbstractService<Assistant, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AssistantTutorialRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Tutorial object;
		Assistant assistant;
		assistant = this.repository.findOneAssistantById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Tutorial();
		object.setDraft(true);
		object.setAssistant(assistant);
		//		object.setCourse(new Course());
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Tutorial object) {
		assert object != null;
		int courseId;
		Course course;
		super.bind(object, "code", "title", "abstractStr", "goals", "estimatedTotalTime", "draft");

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);
		object.setCourse(course);
	}

	@Override
	public void validate(final Tutorial object) {
		String code;
		code = super.getRequest().getData("code", String.class);
		Optional<Tutorial> tutorialWithSameCode;
		tutorialWithSameCode = this.repository.findTutorialByCode(code);
		super.state(!tutorialWithSameCode.isPresent(), "code", "assistant.tutorial.form.error.code.duplicated");

	}

	@Override
	public void perform(final Tutorial object) {
		assert object != null;
		Assistant assistant;
		assistant = this.repository.findOneAssistantById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setAssistant(assistant);

		this.repository.save(object);
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
