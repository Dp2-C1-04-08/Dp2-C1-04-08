
package acme.features.company.practicumSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicumSessions.PracticumSession;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionDeleteService extends AbstractService<Company, PracticumSession> {

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
		final int id = super.getRequest().getData("id", int.class);
		final PracticumSession practicumSession = this.repository.findSessionById(id);
		super.getBuffer().setData(practicumSession);
	}
	@Override
	public void bind(final PracticumSession object) {
		assert object != null;
		super.bind(object, "title", "abstractStr", "link", "startDate", "endDate");

	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;

		final boolean isPublished = object.getPracticum().getPublished();
		super.state(!isPublished, "title", "company.practicumSession.form.error.delete");

	}
	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		this.repository.delete(object);
	}
	@Override
	public void unbind(final PracticumSession object) {

		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractStr", "link", "startDate", "endDate", "practicum");

		super.getResponse().setData(tuple);

	}

}
