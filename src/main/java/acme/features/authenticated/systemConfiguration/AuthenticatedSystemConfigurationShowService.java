/*
 * AuthenticatedAnnouncementShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.systemConfiguration.SystemConfiguration;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedSystemConfigurationShowService extends AbstractService<Authenticated, SystemConfiguration> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedSystemConfigurationRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	//	@Override
	//	public void authorise() {
	//		boolean status;
	//		int id;
	//		SystemConfiguration systemConfiguration;
	//
	//		id = super.getRequest().getData("id", int.class);
	//		systemConfiguration = this.repository.findOneSystemConfigurationById(id);
	//		status = MomentHelper.isAfter(systemConfiguration.getMoment(), deadline);
	//
	//		super.getResponse().setAuthorised(status);
	//	}

	@Override
	public void load() {
		SystemConfiguration object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneSystemConfigurationById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final SystemConfiguration object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "systemCurrency", "acceptedCurrencies");

		super.getResponse().setData(tuple);
	}

}
