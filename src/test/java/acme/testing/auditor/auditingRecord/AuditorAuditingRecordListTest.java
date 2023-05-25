
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.Audit;
import acme.testing.TestHarness;

public class AuditorAuditingRecordListTest extends TestHarness {

	@Autowired
	AuditorAuditingRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecord, final int recordIndex, final String subject, final String mark, final String correction) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecord);
		super.checkFormExists();

		super.clickOnButton("Auditing records");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, subject);
		super.checkColumnHasValue(recordIndex, 1, mark);
		super.checkColumnHasValue(recordIndex, 2, correction);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		//There is no negative test case
	}

	@Test
	public void test300Hacking() {

		super.signIn("auditor1", "auditor1");

		final Collection<Audit> audits = this.repository.findManyAuditsOfOtherAuditors("auditor1");
		for (final Audit audit : audits) {
			final String query = String.format("masterId=%d", audit.getId());
			super.request("/auditor/auditing-record/list", query);
			super.checkPanicExists();
		}

		super.signOut();

	}

}
