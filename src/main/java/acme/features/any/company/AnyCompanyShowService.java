
package acme.features.any.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class AnyCompanyShowService extends AbstractService<Any, Company> {

	@Autowired
	protected AnyCompanyRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Company company;
		int id;
		id = super.getRequest().getData("masterId", int.class);
		company = this.repository.findCompanyById(id);

		super.getBuffer().setData(company);
	}

	@Override
	public void unbind(final Company object) {

		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "name", "vatNumber", "summary", "link");
		super.getResponse().setData(tuple);
	}

}
