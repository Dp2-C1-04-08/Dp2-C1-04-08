
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicums.Practicum;
import acme.features.company.practica.CompanyPracticaRepository;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticumListService extends AbstractService<Authenticated, Practicum> {

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
		final int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		practicums = this.repository.findAllPracticaByCourseId(masterId);
		super.getBuffer().setData(practicums);
	}

	@Override
	public void unbind(final Practicum object) {

		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "code", "title", "goals", "abstractStr", "estimatedTime", "estimatedTime", "company", "published", "course");

		super.getResponse().setData(tuple);
	}

}
