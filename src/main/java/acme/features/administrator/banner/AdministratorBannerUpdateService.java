
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.Banner;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AdministratorBannerUpdateService extends AbstractService<Administrator, Banner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorBannerRepository repository;

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
		Banner object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneBannerById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "image", "slogan", "link", "startDate", "endDate");
	}

	@Override
	public void validate(final Banner object) {
		Date moment;
		moment = MomentHelper.getCurrentMoment();
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = super.getRequest().getData("startDate", Date.class);
		} catch (final Exception e) {
		}
		try {
			endDate = super.getRequest().getData("endDate", Date.class);
		} catch (final Exception e) {
		}
		if (startDate != null) {
			final Boolean startAfterInstantiation = MomentHelper.isAfter(startDate, moment);
			super.state(startAfterInstantiation, "startDate", "administrator.banner.form.error.start-date.invalid");
			if (endDate != null) {
				final Boolean endAfterStart = MomentHelper.isAfter(endDate, startDate);
				super.state(endAfterStart, "endDate", "administrator.banner.form.error.end-date.invalid");
				final Boolean weekOrLongerPeriod = MomentHelper.isLongEnough(startDate, endDate, 7, ChronoUnit.DAYS);
				super.state(weekOrLongerPeriod, "endDate", "administrator.banner.form.error.period.invalid");
			}
		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		Date moment;
		moment = MomentHelper.getCurrentMoment();
		object.setInstantiationMoment(moment);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "slogan", "image", "link", "startDate", "endDate");
		tuple.put("readonly", false);
		super.getResponse().setData(tuple);
	}

}
