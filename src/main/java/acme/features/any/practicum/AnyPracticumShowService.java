
package acme.features.any.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.practicums.Practicum;
import acme.features.company.practica.CompanyPracticaRepository;
import acme.framework.components.accounts.Any;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyPracticumShowService extends AbstractService<Any, Practicum> {

	@Autowired
	protected CompanyPracticaRepository repository;


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
		Practicum practicum;
		int id;
		id = super.getRequest().getData("id", int.class);
		practicum = this.repository.findPracticaById(id);
		super.getBuffer().setData(practicum);
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
