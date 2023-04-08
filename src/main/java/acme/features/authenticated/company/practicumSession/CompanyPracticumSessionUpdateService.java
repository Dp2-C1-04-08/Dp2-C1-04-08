
package acme.features.authenticated.company.practicumSession;

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
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionUpdateService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Company.class);
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

		super.bind(object, "title", "abstractStr", "link", "startDate", "endDate");

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

		final boolean isPublished = object.getPracticum().getPublished();
		super.state(!isPublished, "title", "company.practicumSession.form.error.isPublished");

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

		super.getResponse().setData(tuple);
	}

}
