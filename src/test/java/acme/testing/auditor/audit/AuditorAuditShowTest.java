
package acme.testing.auditor.audit;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.Audit;
import acme.testing.TestHarness;

public class AuditorAuditShowTest extends TestHarness {

	@Autowired
	AuditorAuditTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/audit/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String conclusion, final String strongPoints, final String weakPoints, final String course, final String mark, final String published) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "My audits");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("conclusion", conclusion);
		super.checkInputBoxHasValue("strongPoints", strongPoints);
		super.checkInputBoxHasValue("weakPoints", weakPoints);
		super.checkInputBoxHasValue("published", published);

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
			final String query = String.format("id=%d", audit.getId());
			super.request("/auditor/audit/show", query);
			super.checkPanicExists();
		}

		super.signOut();

	}

}
