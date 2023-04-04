
package acme.features.authenticated.company.practica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicums.Practicum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticaDeleteService extends AbstractService<Company, Practicum> {

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
		super.bind(object, "code", "title", "goals", "abstractStr", "estimatedTime", "estimatedTime", "company");

	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;
	}
	@Override
	public void perform(final Practicum object) {
		assert object != null;

		this.repository.delete(object);
	}
	@Override
	public void unbind(final Practicum object) {

		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "goals", "abstractStr", "estimatedTime", "estimatedTime", "company");

		super.getResponse().setData(tuple);

	}
}
