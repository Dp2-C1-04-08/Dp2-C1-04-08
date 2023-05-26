
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordUpdateTest extends TestHarness {

	@Autowired
	AuditorAuditingRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecord, final int recordIndex, final String subject, final String assessment, final String startDate, final String endDate, final String mark, final String link) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecord);
		super.checkFormExists();

		super.clickOnButton("Auditing records");
		super.checkListingExists();

		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");

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
		super.checkColumnHasValue(recordIndex, 2, "false");

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int auditRecord, final int recordIndex, final String subject, final String assessment, final String startDate, final String endDate, final String mark, final String link) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecord);
		super.checkFormExists();

		super.clickOnButton("Auditing records");
		super.checkListingExists();

		super.sortListing(0, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		String param;
		Collection<AuditingRecord> records;

		records = this.repository.findRecordsByAuditCode("A1111");

		for (final AuditingRecord record : records) {
			param = String.format("id=%d", record.getId());

			super.checkLinkExists("Sign in");
			super.request("/auditor/auditing-record/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/auditor/auditing-record/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor2", "auditor2");
			super.request("/auditor/auditing-record/update", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
