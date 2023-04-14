
package acme.features.company.practicumSession;

import java.time.Duration;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.features.company.practica.CompanyPracticaRepository;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionCreateService extends AbstractService<Company, PracticumSession> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionRepository	repository;

	@Autowired
	CompanyPracticaRepository					companyPracticaRepository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		Boolean status;
		status = super.getRequest().hasData("masterId");
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		final Integer numberAddendum;
		final int masterId = super.getRequest().getData("masterId", int.class);
		final int companyId = super.getRequest().getPrincipal().getActiveRoleId();

		final Practicum practicum = this.repository.findPracticumById(masterId);
		status = practicum.getCompany().getId() == companyId;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		final PracticumSession practicumSession = new PracticumSession();
		final Practicum practicum;

		final int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		practicum = this.repository.findPracticumById(masterId);
		practicumSession.setPracticum(practicum);
		if (practicum.getPublished() == true)
			practicumSession.setAddendum(true);
		else
			practicumSession.setAddendum(false);

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
			final Duration durationAhead = MomentHelper.computeDuration(moment, startDate);
			final Duration minimunDurationAhead = Duration.ofDays(7);
			final Boolean oneWeekAhead = minimunDurationAhead.minus(durationAhead).isNegative();
			super.state(oneWeekAhead, "startDate", "company.practicumSession.form.error.oneWeekAhead");
			if (endDate != null) {
				final Duration durationLong = MomentHelper.computeDuration(startDate, endDate);
				final Duration minimunDurationLong = Duration.ofDays(7);
				final Boolean oneWeekLong = minimunDurationLong.minus(durationLong).isNegative();
				super.state(oneWeekLong, "endDate", "company.practicumSession.form.error.oneWeekLong");
			}
		}
		final int numberAddendum = this.repository.countNumberOfAddendum(object.getPracticum().getId());
		final boolean canAddendum = numberAddendum < 1;
		super.state(canAddendum, "*", "company.practicumSession.form.error.canAddendum");

	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;
		final Date startDate = super.getRequest().getData("startDate", Date.class);
		final Date endDate = super.getRequest().getData("endDate", Date.class);
		final Duration durationLong = MomentHelper.computeDuration(startDate, endDate);
		final long daysLong = durationLong.toDays();
		final Practicum practicum = object.getPracticum();
		final Integer duration = (int) (practicum.getEstimatedTime() + daysLong);
		practicum.setEstimatedTime(duration);
		this.repository.save(object);
		this.companyPracticaRepository.save(practicum);
	}

	@Override
	public void unbind(final PracticumSession object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "abstractStr", "link", "startDate", "endDate", "practicum");
		tuple.put("masterId", super.getRequest().getData("masterId", int.class));

		super.getResponse().setData(tuple);
	}

}
