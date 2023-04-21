
package acme.features.auditor.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.forms.AuditorDashboard;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
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

		Collection<Audit> audits = null;
		Collection<AuditingRecord> auditingRecords = null;
		final List<Integer> numberOfRecordsPerAudit = new ArrayList<>();
		final List<Long> durationsOfRecords = new ArrayList<>();

		int numberOfAuditsForTheoricalCourses = 0;
		Integer numberOfAuditsForHandsOnCourses = 0;
		double averageNumberOfRecordsInAudit = 0.0;
		double deviationNumberOfRecordsInAudit = 0.0;
		double minimumNumberOfRecordsInAudit = 0.0;
		double maximumNumberOfRecordsInAudit = 0.0;
		double averageLengthOfPeriodInRecords = 0.0;
		double deviationLengthOfPeriodInRecords = 0.0;
		double minimumLengthOfPeriodInRecords = 0.0;
		double maximumLengthOfPeriodInRecords = 0.0;

		audits = this.repository.findAllAuditsForAuditor(auditorId);

		if (audits != null)
			for (final Audit audit : audits) {
				final Integer aux = this.repository.countRecordsInAudit(audit.getId());
				if (aux != null)
					numberOfRecordsPerAudit.add(aux);
				else
					numberOfRecordsPerAudit.add(0);
			}

		int mean = 0;
		int auxSum = 0;
		int tempAccum = 0;
		double meanOfDiffs = 0.0;
		if (!numberOfRecordsPerAudit.isEmpty()) {
			for (final Integer i : numberOfRecordsPerAudit)
				auxSum += i;

			mean = auxSum / numberOfRecordsPerAudit.size();

			for (final Integer j : numberOfRecordsPerAudit) {
				final double squrDiffToMean = Math.pow(j - mean, 2);
				tempAccum += squrDiffToMean;
			}
			meanOfDiffs = (double) tempAccum / (double) numberOfRecordsPerAudit.size();
		}

		deviationNumberOfRecordsInAudit = Math.sqrt(meanOfDiffs);

		auditingRecords = this.repository.findAllRecordsForAuditor(auditorId);
		if (auditingRecords != null)
			for (final AuditingRecord ar : auditingRecords) {
				final Long duration = MomentHelper.computeDuration(ar.getStartDate(), ar.getEndDate()).getSeconds();
				durationsOfRecords.add(duration);
			}

		if (!durationsOfRecords.isEmpty()) {
			minimumLengthOfPeriodInRecords = Collections.min(durationsOfRecords);
			maximumLengthOfPeriodInRecords = Collections.max(durationsOfRecords);
		}

		Long mean2 = 0L;
		long auxSum2 = 0L;
		double tempAccum2 = 0.0;
		double meanOfDiffs2 = 0.0;

		if (!durationsOfRecords.isEmpty()) {
			for (final Long d : durationsOfRecords)
				auxSum2 += d;

			mean2 = auxSum2 / durationsOfRecords.size();

			for (final Long j : durationsOfRecords) {
				final double squrDiffToMean2 = Math.pow(j - mean, 2);
				tempAccum2 += squrDiffToMean2;
			}

			meanOfDiffs2 = tempAccum2 / durationsOfRecords.size();
		}

		deviationLengthOfPeriodInRecords = Math.sqrt(meanOfDiffs2);
		averageLengthOfPeriodInRecords = mean2;

		numberOfAuditsForTheoricalCourses = this.repository.numberOfAuditsForTheoreticalCourses(auditorId);
		numberOfAuditsForHandsOnCourses = this.repository.numberOfAuditsForHandsOnCourses(auditorId);

		if (this.repository.countRecordsForAuditor(auditorId) != 0) {
			averageNumberOfRecordsInAudit = this.repository.averageNumberOfRecordsInAudit(auditorId);
			maximumNumberOfRecordsInAudit = this.repository.maximumNumberOfRecordsInAudit(auditorId);
			minimumNumberOfRecordsInAudit = this.repository.minimumNumberOfRecordsInAudit(auditorId);
		}

		dashboard = new AuditorDashboard();
		dashboard.setNumberOfAuditsForTheoreticalCourses(numberOfAuditsForTheoricalCourses);
		dashboard.setNumberOfAuditsForHandsOnCourses(numberOfAuditsForHandsOnCourses);
		dashboard.setAverageNumberOfRecordsInAudit(averageNumberOfRecordsInAudit);
		dashboard.setDeviationNumberOfRecordsInAudit(deviationNumberOfRecordsInAudit);
		dashboard.setMaximumNumberOfRecordsInAudit(maximumNumberOfRecordsInAudit);
		dashboard.setMinimumNumberOfRecordsInAudit(minimumNumberOfRecordsInAudit);
		dashboard.setAverageLengthOfPeriodInRecords(averageLengthOfPeriodInRecords);
		dashboard.setDeviationLengthOfPeriodInRecords(deviationLengthOfPeriodInRecords);
		dashboard.setMaximumLengthOfPeriodInRecords(maximumLengthOfPeriodInRecords);
		dashboard.setMinimumLengthOfPeriodInRecords(minimumLengthOfPeriodInRecords);

		super.getBuffer().setData(dashboard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Tuple tuple = null;

		tuple = super.unbind(object,  //
			"numberOfAuditsForTheoreticalCourses", "numberOfAuditsForHandsOnCourses", "averageNumberOfRecordsInAudit", "deviationNumberOfRecordsInAudit", "maximumNumberOfRecordsInAudit", //
			"minimumNumberOfRecordsInAudit", "averageLengthOfPeriodInRecords", "deviationLengthOfPeriodInRecords", "maximumLengthOfPeriodInRecords", "minimumLengthOfPeriodInRecords");

		super.getResponse().setData(tuple);
	}

}
