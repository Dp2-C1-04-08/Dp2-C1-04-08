
package acme.features.company.practicumSession;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class CompanyPracticumSessionListService extends AbstractService<Company, PracticumSession> {

	@Autowired
	protected CompanyPracticumSessionRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("masterId");
		super.getResponse().setChecked(status);

	}

	@Override
	public void authorise() {
		boolean status;

		final int masterId = super.getRequest().getData("masterId", int.class);
		final int companyId = super.getRequest().getPrincipal().getActiveRoleId();
		super.getResponse().setGlobal("masterId", masterId);
		final Practicum practicum = this.repository.findPracticumById(masterId);
		status = super.getRequest().getPrincipal().hasRole(Company.class) && practicum.getCompany().getId() == companyId;
		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		List<PracticumSession> practicumSessions;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		practicumSessions = this.repository.findAllSessionByPracticum(masterId);
		super.getBuffer().setData(practicumSessions);
	}

	@Override
	public void unbind(final PracticumSession object) {

		assert object != null;
		Tuple tuple;
		final LocalDateTime initialDate = Instant.ofEpochMilli(object.getStartDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
		final LocalDateTime endDate = Instant.ofEpochMilli(object.getEndDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
		final Duration duracion = Duration.between(initialDate, endDate);
		final long days = duracion.toDays();
		final int masterId = super.getRequest().getData("masterId", int.class);
		tuple = super.unbind(object, "title", "abstractStr", "link", "practicum", "addendum");

		tuple.put("duration", days);
		tuple.put("masterId", masterId);
		super.getResponse().setData(tuple);
	}

}
