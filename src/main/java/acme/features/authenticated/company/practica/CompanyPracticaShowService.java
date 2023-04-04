
package acme.features.authenticated.company.practica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicums.Practicum;
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
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Company.class);
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

		tuple = super.unbind(object, "code", "title", "goals", "abstractStr", "estimatedTime", "estimatedTime", "company");

		super.getResponse().setData(tuple);
	}

}
