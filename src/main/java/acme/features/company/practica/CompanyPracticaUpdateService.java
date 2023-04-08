
package acme.features.company.practica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicums.Practicum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticaUpdateService extends AbstractService<Company, Practicum> {

	@Autowired
	protected CompanyPracticaRepository repository;


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

		super.bind(object, "code", "title", "goals", "abstractStr", "estimatedTime");

	}
	@Override
	public void validate(final Practicum object) {
		assert object != null;
		final boolean isPublished = object.getPublished();
		super.state(!isPublished, "title", "company.practica.form.error.update.published");

	}

	@Override
	public void perform(final Practicum object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Practicum object) {

		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "goals", "abstractStr", "estimatedTime", "estimatedTime", "published");

		super.getResponse().setData(tuple);
	}

}
