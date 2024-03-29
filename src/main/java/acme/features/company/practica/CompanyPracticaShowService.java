
package acme.features.company.practica;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.entities.practicums.Practicum;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticaShowService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticaRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
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
		final Float minus10estimatedTime = (float) (object.getEstimatedTime() - 0.1 * object.getEstimatedTime());
		final Float plus10estimatedTime = (float) (object.getEstimatedTime() + 0.1 * object.getEstimatedTime());

		tuple = super.unbind(object, "code", "title", "goals", "abstractStr", "estimatedTime", "estimatedTime", "company", "published");
		tuple.put("course", choices.getSelected().getKey());
		tuple.put("courses", choices);
		tuple.put("minus10estimatedTime", minus10estimatedTime);
		tuple.put("plus10estimatedTime", plus10estimatedTime);
		super.getResponse().setData(tuple);
	}

}
