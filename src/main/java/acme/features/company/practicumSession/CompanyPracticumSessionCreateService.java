
package acme.features.company.practicumSession;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionCreateService extends AbstractService<Company, PracticumSession> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumSessionRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		Integer numberAddendum;
		final int masterId = super.getRequest().getData("masterId", int.class);
		numberAddendum = this.repository.countNumberOfAddendum(masterId);
		status = numberAddendum < 1;
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

		final String startDateStr = this.getRequest().getData("startDate", String.class);
		final String endDateStr = this.getRequest().getData("endDate", String.class);
		final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			final Date startDate = format.parse(startDateStr);
			final Date endDate = format.parse(endDateStr);
			object.setStartDate(startDate);
			object.setEndDate(endDate);
		} catch (final ParseException e) {

			e.printStackTrace();
		}

		super.bind(object, "title", "abstractStr", "link");
	}

	@Override
	public void validate(final PracticumSession object) {
		assert object != null;

		final LocalDateTime initialDate = Instant.ofEpochMilli(object.getStartDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
		final LocalDateTime endDate = Instant.ofEpochMilli(object.getEndDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
		final Duration durationLong = Duration.between(initialDate, endDate);
		final long daysLong = durationLong.toDays();
		final boolean isOneWeekLong = daysLong >= 7;
		super.state(isOneWeekLong, "endDate", "company.practicumSession.form.error.oneWeekLong");

		final Date actualDate = MomentHelper.getCurrentMoment();
		final LocalDateTime actualDateLocalTime = Instant.ofEpochMilli(actualDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
		final Duration durationAhead = Duration.between(actualDateLocalTime, initialDate);
		final Long daysAhead = durationAhead.toDays();
		final boolean isOneWeekAhead = daysAhead >= 7;
		super.state(isOneWeekAhead, "startDate", "company.practicumSession.form.error.oneWeekAhead");

	}

	@Override
	public void perform(final PracticumSession object) {
		assert object != null;

		this.repository.save(object);
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
