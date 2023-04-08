
package acme.features.company.practica;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.features.company.practicumSession.CompanyPracticumSessionRepository;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticaDeleteService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticaRepository			repository;

	@Autowired
	protected CompanyPracticumSessionRepository	practicumSessionRepository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Company.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Practicum practicum = this.repository.findPracticaById(id);
		super.getBuffer().setData(practicum);
	}
	@Override
	public void bind(final Practicum object) {
		assert object != null;
		super.bind(object, "code", "title", "goals", "abstractStr", "published");

	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;
		final boolean isPublished = object.getPublished();
		super.state(!isPublished, "title", "company.practica.form.error.delete.published");

	}
	@Override
	public void perform(final Practicum object) {
		assert object != null;
		final List<PracticumSession> practicumSessions = this.practicumSessionRepository.findAllSessionByPracticum(object.getId());
		if (practicumSessions != null && practicumSessions.size() > 0)
			for (final PracticumSession ps : practicumSessions)
				this.practicumSessionRepository.delete(ps);

		this.repository.delete(object);
	}
	@Override
	public void unbind(final Practicum object) {

		assert object != null;
		Tuple tuple;
		final Collection<Course> courses = this.repository.findAllCourse();
		final SelectChoices choices = SelectChoices.from(courses, "title", object.getCourse());
		tuple = super.unbind(object, "code", "title", "goals", "abstractStr", "estimatedTime", "estimatedTime", "company", "published");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		super.getResponse().setData(tuple);

	}
}
