
package acme.features.authenticated.audits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedAuditShowService extends AbstractService<Authenticated, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		final Audit object;
		int id;
		final Boolean status;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditById(id);

		status = object != null && object.getPublished() && !object.getCourse().isDraft();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Audit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;
		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "mark");
		tuple.put("course", object.getCourse().getCode() + " - " + object.getCourse().getTitle());
		final String fullName = object.getAuditor().getUserAccount().getIdentity().getFullName();
		tuple.put("auditor", fullName);

		super.getResponse().setData(tuple);
	}

}
