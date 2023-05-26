
package acme.features.company.practicumSession;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
public class CompanyPracticumSessionUpdateService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository	repository;

	@Autowired
	CompanyPracticaRepository					companyPracticaRepository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Company.class);

		final int practicumId = super.getRequest().getData("id", int.class);
		final int companyId = super.getRequest().getPrincipal().getActiveRoleId();
		boolean sameCompany;
		final PracticumSession practicumSession = this.repository.findSessionById(practicumId);
		sameCompany = practicumSession.getPracticum().getCompany().getId() == companyId;
		status = status && sameCompany;

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

		super.bind(object, "title", "abstractStr", "link", "startDate", "endDate");

	}
	@Override
	public void validate(final PracticumSession object) {

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

		if (startDate != null && endDate != null) {

			object.setStartDate(startDate);
			object.setEndDate(endDate);

			final LocalDateTime initialDate = Instant.ofEpochMilli(object.getStartDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
			final LocalDateTime endDateDuration = Instant.ofEpochMilli(object.getEndDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
			final Duration durationLong = Duration.between(initialDate, endDateDuration);
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
		final boolean isPublished = object.getPracticum().getPublished();
		super.state(!isPublished, "title", "company.practicumSession.form.error.isPublished");

	}
	@Override
	public void perform(final PracticumSession object) {
		assert object != null;
		final LocalDateTime initialDate = Instant.ofEpochMilli(object.getStartDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
		final LocalDateTime endDate = Instant.ofEpochMilli(object.getEndDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
		final Duration durationLong = Duration.between(initialDate, endDate);
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

		super.getResponse().setData(tuple);
	}

}
