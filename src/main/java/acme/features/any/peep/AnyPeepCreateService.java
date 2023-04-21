
package acme.features.any.peep;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.peeps.Peep;
import acme.framework.components.accounts.Anonymous;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AnyPeepCreateService extends AbstractService<Any, Peep> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyPeepRepository repository;

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
		Peep object;

		object = new Peep();
		if (!super.getRequest().getPrincipal().hasRole(Anonymous.class))
			object.setNick(super.getRequest().getPrincipal().getUsername());

		Date instanciationMoment;
		instanciationMoment = MomentHelper.getCurrentMoment();
		object.setInstanciationMoment(instanciationMoment);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Peep object) {
		assert object != null;
		super.bind(object, "title", "message", "email", "link", "nick");
	}

	@Override
	public void validate(final Peep object) {
		assert object != null;
	}

	@Override
	public void perform(final Peep object) {
		assert object != null;
		Date instanciationMoment;
		instanciationMoment = MomentHelper.getCurrentMoment();
		object.setInstanciationMoment(instanciationMoment);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Peep object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "nick", "instanciationMoment", "message", "email", "link");

		super.getResponse().setData(tuple);

	}

}
