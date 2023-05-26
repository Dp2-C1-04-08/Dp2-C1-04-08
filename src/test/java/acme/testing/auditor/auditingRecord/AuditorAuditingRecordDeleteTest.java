
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.AuditingRecord;
import acme.testing.TestHarness;

public class AuditorAuditingRecordDeleteTest extends TestHarness {

	@Autowired
	AuditorAuditingRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecord, final int recordIndex) {

		super.signIn("auditor3", "auditor3");

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

		super.clickOnSubmit("Delete");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecord);
		super.checkFormExists();

		super.clickOnButton("Auditing records");
		super.checkListingEmpty();

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int auditRecord, final int recordIndex) {

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

		super.checkNotButtonExists("Delete");

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
			super.request("/auditor/auditing-record/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/auditor/auditing-record/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor2", "auditor2");
			super.request("/auditor/auditing-record/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
