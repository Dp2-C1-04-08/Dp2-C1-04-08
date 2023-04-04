
package acme.features.authenticated.company.practica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicums.Practicum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticaCreateService extends AbstractService<Company, Practicum> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticaRepository repository;

	// AbstractService interface ----------------------------------------------


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
		final Practicum practicum = new Practicum();
		Company company;
		company = this.repository.findCompanyById(super.getRequest().getPrincipal().getActiveRoleId());
		practicum.setCompany(company);
		super.getBuffer().setData(practicum);
	}

	@Override
	public void bind(final Practicum object) {
		assert object != null;

		super.bind(object, "code", "title", "goals", "abstractStr", "estimatedTime", "estimatedTime");
	}

	@Override
	public void validate(final Practicum object) {
		assert object != null;

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

		tuple = super.unbind(object, "code", "title", "goals", "abstractStr", "estimatedTime");

		super.getResponse().setData(tuple);
	}

}
