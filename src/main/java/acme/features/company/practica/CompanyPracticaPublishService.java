
package acme.features.company.practica;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.practicums.Practicum;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticaPublishService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticaRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		final int companyId = super.getRequest().getPrincipal().getActiveRoleId();
		final int practicumId = super.getRequest().getData("id", int.class);
		final Practicum practicum = this.repository.findPracticaById(practicumId);
		final boolean sameCompany = practicum.getCompany().getId() == companyId;
		status = super.getRequest().getPrincipal().hasRole(Company.class) && sameCompany;
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
		final int courseId = super.getRequest().getData("course", int.class);
		final Course course = this.repository.findCourseById(courseId);
		object.setCourse(course);

		super.bind(object, "code", "title", "goals", "abstractStr", "published");

	}
	@Override
	public void validate(final Practicum object) {
		assert object != null;
		String code;
		final Boolean sameCode;
		Boolean noSessionInPracticum;

		final boolean isPublished = object.getPublished();
		super.state(!isPublished, "*", "company.practica.form.error.update.published");

		Optional<Practicum> practicumWhithSameCode;
		Practicum practicum;

		code = super.getRequest().getData("code", String.class);
		practicumWhithSameCode = this.repository.findOnePracticumByCode(code);
		practicum = practicumWhithSameCode.get();
		sameCode = !practicumWhithSameCode.isPresent() || practicum.getId() == object.getId();
		super.state(sameCode, "*", "company.practica.form.error.code");

		final Integer sessions = this.repository.getCountPracticumSessionOfPracticum(object.getId());
		noSessionInPracticum = sessions > 0;
		super.state(noSessionInPracticum, "*", "company.practica.form.error.noSessions");

	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;

		object.setPublished(true);
		this.repository.save(object);
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
