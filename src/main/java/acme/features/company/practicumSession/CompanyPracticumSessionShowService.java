
package acme.features.company.practicumSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicumSessions.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionShowService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repository;


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
		PracticumSession practicumSession;
		int id;
		id = super.getRequest().getData("id", int.class);
		practicumSession = this.repository.findSessionById(id);
		super.getBuffer().setData(practicumSession);
	}

	@Override
	public void unbind(final PracticumSession object) {

		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractStr", "link", "startDate", "endDate", "practicum");
		tuple.put("masterId", object.getPracticum().getId());
		super.getResponse().setData(tuple);
	}

}
