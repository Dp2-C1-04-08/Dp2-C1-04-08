
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.Audit;
import acme.testing.TestHarness;

public class AuditorAuditingRecordCreateTest extends TestHarness {

	@Autowired
	AuditorAuditingRecordTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int auditRecord, final int recordIndex, final String subject, final String assessment, final String startDate, final String endDate, final String mark, final String link) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecord);
		super.checkFormExists();

		super.clickOnButton("Auditing records");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Create");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecord);
		super.checkFormExists();

		super.clickOnButton("Auditing records");
		super.checkListingExists();

		super.checkColumnHasValue(recordIndex, 0, subject);
		super.checkColumnHasValue(recordIndex, 1, mark);
		super.checkColumnHasValue(recordIndex, 2, "false");

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/create-positive2.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test102Positive(final int auditRecord, final int recordIndex, final String subject, final String assessment, final String startDate, final String endDate, final String mark, final String link, final String confirmation) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecord);
		super.checkFormExists();

		super.clickOnButton("Auditing records");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("confirmation", confirmation);

		super.clickOnSubmit("Create");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecord);
		super.checkFormExists();

		super.clickOnButton("Auditing records");
		super.checkListingExists();

		super.checkColumnHasValue(recordIndex, 0, subject);
		super.checkColumnHasValue(recordIndex, 1, mark);
		super.checkColumnHasValue(recordIndex, 2, "true");

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditingRecord/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int auditRecord, final int recordIndex, final String subject, final String assessment, final String startDate, final String endDate, final String mark, final String link) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(auditRecord);
		super.checkFormExists();

		super.clickOnButton("Auditing records");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("subject", subject);
		super.fillInputBoxIn("assessment", assessment);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		String param;
		Collection<Audit> audits;

		audits = this.repository.findManyAuditsOfOtherAuditors("auditor1");

		for (final Audit audit : audits) {
			param = String.format("masterId=%d", audit.getId());

			super.checkLinkExists("Sign in");
			super.request("/auditor/auditing-record/create", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/auditor/auditing-record/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("auditor1", "auditor1");
			super.request("/auditor/auditing-record/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}

}
