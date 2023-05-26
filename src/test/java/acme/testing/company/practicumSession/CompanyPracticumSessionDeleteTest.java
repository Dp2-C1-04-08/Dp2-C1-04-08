
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicumSessions.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionDeleteTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int sessionRecord, final int practicumRecord) {

		super.signIn("company1", "company1");

		super.clickOnMenu("My Company", "List Practicum");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(practicumRecord);
		super.checkFormExists();

		super.clickOnButton("List Sessions");
		super.checkListingExists();
		super.sortListing(2, "asc");

		super.clickOnListingRecord(sessionRecord);
		super.checkFormExists();

		super.clickOnSubmit("Delete");

		super.clickOnMenu("My Company", "List Practicum");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(practicumRecord);
		super.checkFormExists();

		super.clickOnButton("List Sessions");
		super.checkListingEmpty();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int sessionRecord, final int practicumRecord) {

		super.signIn("company1", "company1");

		super.clickOnMenu("My Company", "List Practicum");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(practicumRecord);
		super.checkFormExists();

		super.clickOnButton("List Sessions");
		super.checkListingExists();
		super.sortListing(2, "asc");

		super.clickOnListingRecord(sessionRecord);
		super.checkFormExists();

		super.checkNotButtonExists("Delete");

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		String param;
		Collection<PracticumSession> practicumSessions;

		practicumSessions = this.repository.findSessionsByCompanyName("company1");

		for (final PracticumSession session : practicumSessions) {
			param = String.format("masterId=%d", session.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/company/practicum-session/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
