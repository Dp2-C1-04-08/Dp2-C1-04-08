
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
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Company.class);

		final int practicumId = super.getRequest().getData("id", int.class);
		final int companyId = super.getRequest().getPrincipal().getActiveRoleId();
		boolean sameCompany;
		final PracticumSession practicumSession = this.repository.findSessionById(practicumId);
		sameCompany = practicumSession.getPracticum().getCompany().getId() == companyId;
		status = status && sameCompany;

		super.getResponse().setAuthorised(status);
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
		tuple.put("practicumPublished", object.getPracticum().getPublished());
		super.getResponse().setData(tuple);
	}

}
