
package acme.features.authenticated.peep;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.peeps.Peep;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPeepListService extends AbstractService<Authenticated, Peep> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedPeepRepository repository;

	// AbstractService interface ----------------------------------------------


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
		Collection<Peep> objects;
		Date deadline;

		deadline = MomentHelper.deltaFromCurrentMoment(-30, ChronoUnit.DAYS);
		objects = this.repository.findRecentAnnouncements(deadline);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Peep object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "nick", "instanciationMoment", "message", "email", "link");

		super.getResponse().setData(tuple);
	}

}
