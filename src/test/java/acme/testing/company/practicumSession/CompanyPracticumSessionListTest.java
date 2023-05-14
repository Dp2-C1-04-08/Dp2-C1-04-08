
package acme.testing.company.practicumSession;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicumSessions.PracticumSession;
import acme.testing.TestHarness;

public class CompanyPracticumSessionListTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int sessionRecord, final int practicumRecord, final String title, final String addendum, final String link, final String duration) {

		super.signIn("company1", "company1");

		super.clickOnMenu("My Company", "List Practicum");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(practicumRecord);
		super.checkFormExists();

		super.clickOnButton("List Sessions");
		super.checkListingExists();
		super.sortListing(2, "asc");

		super.checkColumnHasValue(sessionRecord, 0, title);
		super.checkColumnHasValue(sessionRecord, 1, addendum);

		super.checkColumnHasValue(sessionRecord, 2, link);
		super.checkColumnHasValue(sessionRecord, 3, duration);

		super.signOut();
	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {
		String param;
		List<PracticumSession> practicumSessions;

		practicumSessions = this.repository.findSessionsByPracticumTitle("practica1");

		param = String.format("masterId=%d", 108);

		super.checkLinkExists("Sign in");
		super.request("/company/practicum-session/list", param);
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/company/practicum-session/list", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("company2", "company2");
		super.request("/company/practicum-session/list", param);
		super.checkPanicExists();
		super.signOut();

	}

}
