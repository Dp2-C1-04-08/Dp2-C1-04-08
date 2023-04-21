
package acme.features.auditor.dashboard;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Nature;
import acme.forms.AuditorDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorDashboardRepository repository;

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
		final AuditorDashboard dashboard;
		final int auditorId = super.getRequest().getPrincipal().getActiveRoleId();

		final Map<Nature, Long> map = new HashMap<>();
		Collection<javax.persistence.Tuple> numberOfAuditsForCoursesByType = null;
		double averageNumberOfRecordsInAudit = 0.0;
		double deviationNumberOfRecordsInAudit = 0.0;
		double minimumNumberOfRecordsInAudit = 0.0;
		double maximumNumberOfRecordsInAudit = 0.0;
		final double averageLengthOfPeriodInRecords = 0.0;
		final double deviationLengthOfPeriodInRecords = 0.0;
		final double minimumLengthOfPeriodInRecords = 0.0;
		final double maximumLengthOfPeriodInRecords = 0.0;

		numberOfAuditsForCoursesByType = this.repository.numberOfAuditsForCoursesByType(auditorId);
		for (final javax.persistence.Tuple t : numberOfAuditsForCoursesByType)
			map.put((Nature) t.get(0), (long) t.get(1));

		averageNumberOfRecordsInAudit = this.repository.averageNumberOfRecordsInAudit(auditorId);
		deviationNumberOfRecordsInAudit = this.repository.deviationNumberOfRecordsInAudit(auditorId);
		maximumNumberOfRecordsInAudit = this.repository.maximumNumberOfRecordsInAudit(auditorId);
		minimumNumberOfRecordsInAudit = this.repository.minimumNumberOfRecordsInAudit(auditorId);

		dashboard = new AuditorDashboard();
		dashboard.setNumberOfAuditsForCoursesByType(map);
		dashboard.setAverageNumberOfRecordsInAudit(averageNumberOfRecordsInAudit);
		dashboard.setDeviationNumberOfRecordsInAudit(deviationNumberOfRecordsInAudit);
		dashboard.setMaximumNumberOfRecordsInAudit(maximumNumberOfRecordsInAudit);
		dashboard.setMinimumNumberOfRecordsInAudit(minimumNumberOfRecordsInAudit);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Tuple tuple = null;

		tuple = super.unbind(object,  //
			"numberOfAuditsForCoursesByType", "averageNumberOfRecordsInAudit", "deviationNumberOfRecordsInAudit", "maximumNumberOfRecordsInAudit", //
			"minimumNumberOfRecordsInAudit");

		super.getResponse().setData(tuple);
	}

}
