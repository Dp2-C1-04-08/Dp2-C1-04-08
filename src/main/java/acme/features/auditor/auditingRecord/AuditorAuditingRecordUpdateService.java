
package acme.features.auditor.auditingRecord;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.entities.audits.MarkValue;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditingRecordUpdateService extends AbstractService<Auditor, AuditingRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditingRecordRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		AuditingRecord auditingRecord;

		Principal principal;

		principal = super.getRequest().getPrincipal();
		id = super.getRequest().getData("id", int.class);
		auditingRecord = this.repository.findOneAuditingRecordById(id);

		status = auditingRecord != null && !auditingRecord.getAudit().getPublished() && auditingRecord.getAudit().getAuditor().getId() == principal.getActiveRoleId();
		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		AuditingRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditingRecordById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final AuditingRecord object) {
		assert object != null;
		super.bind(object, "subject", "assessment", "startDate", "endDate", "mark", "link", "correction");
	}

	@Override
	public void validate(final AuditingRecord object) {
		Date startDate = null;
		Date endDate = null;
		MarkValue mark = null;
		try {
			startDate = super.getRequest().getData("startDate", Date.class);
		} catch (final Exception e) {
		}
		try {
			endDate = super.getRequest().getData("endDate", Date.class);
		} catch (final Exception e) {
		}
		try {
			mark = super.getRequest().getData("mark", MarkValue.class);
		} catch (final Exception e) {
		}
		if (mark != null)
			super.state(!mark.equals(MarkValue.NA), "mark", "auditor.auditingRecord.form.error.mark.invalid");
		if (endDate != null) {
			final Boolean endAfterStart = MomentHelper.isAfter(endDate, startDate);
			super.state(endAfterStart, "endDate", "auditor.auditingRecord.form.error.end-date.invalid");
			final Boolean hourOrLongerPeriod = MomentHelper.isLongEnough(startDate, endDate, 1, ChronoUnit.HOURS);
			super.state(hourOrLongerPeriod, "endDate", "auditor.auditingRecord.form.error.period.invalid");
		}

	}

	@Override
	public void perform(final AuditingRecord object) {
		assert object != null;

		Audit audit;
		this.repository.save(object);
		long max = 0;
		int index = 0;
		final List<MarkValue> marks = new ArrayList<>();
		final Collection<javax.persistence.Tuple> col = this.repository.countRecordsGroupByMark(object.getAudit().getId());
		for (final javax.persistence.Tuple t : col)
			if ((long) t.get(1) == max)
				marks.add((MarkValue) t.get(0));
			else if ((long) t.get(1) > max) {
				max = (long) t.get(1);
				marks.clear();
				marks.add((MarkValue) t.get(0));
			}
		ThreadLocalRandom random;
		random = ThreadLocalRandom.current();
		index = random.nextInt(0, marks.size());
		audit = this.repository.findOneAuditByAuditingRecordId(object.getId());
		audit.setMark(marks.get(index));
		this.repository.save(audit);
	}

	@Override
	public void unbind(final AuditingRecord object) {
		assert object != null;
		SelectChoices choices;
		Tuple tuple;

		tuple = super.unbind(object, "subject", "assessment", "startDate", "endDate", "mark", "link", "correction");
		tuple.put("published", object.getAudit().getPublished());
		choices = SelectChoices.from(MarkValue.class, object.getMark());
		tuple.put("marks", choices);
		tuple.put("mark", choices.getSelected());

		super.getResponse().setData(tuple);
	}

}
