/*
 * EmployerDutyUpdateService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.offer;

import java.time.Duration;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offers.Offer;
import acme.entities.systemConfiguration.MoneyService;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.datatypes.Money;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorOfferUpdateService extends AbstractService<Administrator, Offer> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorOfferRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Offer object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneOfferById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Offer object) {
		assert object != null;
		super.bind(object, "heading", "summary", "price", "link", "startDate", "endDate");
	}

	@Override
	public void validate(final Offer object) {
		Date moment;
		moment = MomentHelper.getCurrentMoment();
		Date startDate = null;
		Date endDate = null;
		Money price = null;
		try {
			startDate = super.getRequest().getData("startDate", Date.class);
		} catch (final Exception e) {
		}
		try {
			endDate = super.getRequest().getData("endDate", Date.class);
		} catch (final Exception e) {
		}
		try {
			price = super.getRequest().getData("price", Money.class);
			super.state(price.getAmount() > 0, "price", "administrator.offer.form.error.price.negative-or-zero");
			super.state(MoneyService.checkContains(price.getCurrency()), "price", "administrator.offer.form.error.price.invalid-currency");
		} catch (final Exception e) {
		}
		if (startDate != null) {
			final Duration startDelay = MomentHelper.computeDuration(moment, startDate);
			final Duration startMinimumDuration = Duration.ofDays(1);
			final Boolean startDelayBool = startMinimumDuration.minus(startDelay).isNegative();
			super.state(startDelayBool, "startDate", "administrator.offer.form.error.start-date.invalid");
			if (endDate != null) {
				final Duration activeDelay = MomentHelper.computeDuration(startDate, endDate);
				final Duration activeMinimumDuration = Duration.ofDays(7);
				final Boolean activeDelayBool = activeMinimumDuration.minus(activeDelay).isNegative();
				super.state(activeDelayBool, "endDate", "administrator.offer.form.error.end-date.invalid");
			}
		}
	}

	@Override
	public void perform(final Offer object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Offer object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "heading", "summary", "price", "link", "startDate", "endDate");
		tuple.put("readonly", false);
		super.getResponse().setData(tuple);
	}

}
