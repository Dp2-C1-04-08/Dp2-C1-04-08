
package acme.features.company.practica;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicums.Practicum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticaListService extends AbstractService<Company, Practicum> {

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
		Collection<Practicum> practicums;
		int id;
		id = super.getRequest().getPrincipal().getActiveRoleId();
		practicums = this.repository.findPracticaByCompanyId(id);
		super.getBuffer().setData(practicums);
	}

	@Override
	public void unbind(final Practicum object) {

		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "goals", "abstractStr", "estimatedTime", "company", "published");

		super.getResponse().setData(tuple);
	}

}
