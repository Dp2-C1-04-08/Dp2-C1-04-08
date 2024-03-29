/*
 * AdministratorAnnouncementCreateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.bulletin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bulletins.Bulletin;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBulletinCreateService extends AbstractService<Administrator, Bulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBulletinRepository repository;

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
		Bulletin object;
		Date moment;
		moment = MomentHelper.getCurrentMoment();

		object = new Bulletin();
		object.setTitle("");
		object.setMessage("");
		object.setFlag(false);
		object.setLink("");

		object.setInstantiationMoment(moment);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Bulletin object) {
		assert object != null;
		super.bind(object, "title", "message", "flag", "link");
	}

	@Override
	public void validate(final Bulletin object) {
		assert object != null;

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
	}

	@Override
	public void perform(final Bulletin object) {
		assert object != null;

		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setInstantiationMoment(moment);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Bulletin object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "instantiationMoment", "title", "message", "flag", "link");
		tuple.put("confirmation", false);
		super.getResponse().setData(tuple);
	}

}
