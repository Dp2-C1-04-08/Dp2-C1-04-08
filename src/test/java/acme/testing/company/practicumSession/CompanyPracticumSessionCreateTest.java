
package acme.testing.company.practicumSession;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicums.Practicum;
import acme.testing.TestHarness;
import acme.testing.company.practicum.CompanyPracticumTestRepository;

public class CompanyPracticumSessionCreateTest extends TestHarness {

	@Autowired
	protected CompanyPracticumSessionTestRepository	repository;

	@Autowired
	protected CompanyPracticumTestRepository		practicumRepository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int sessionRecord, final int practicumRecord, final String title, final String addendum, final String link, final String duration, final String abstractStr, final String startDate, final String endDate) {

		super.signIn("company1", "company1");

		super.clickOnMenu("My Company", "List Practicum");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(practicumRecord);
		super.checkFormExists();

		super.clickOnButton("List Sessions");
		super.checkListingExists();
		super.sortListing(2, "asc");

		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractStr", abstractStr);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);

		super.clickOnSubmit("Create");

		super.clickOnMenu("My Company", "List Practicum");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(practicumRecord);
		super.checkFormExists();

		super.clickOnButton("List Sessions");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(sessionRecord);

		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractStr", abstractStr);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicumSession/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int sessionRecord, final int practicumRecord, final String title, final String addendum, final String link, final String duration, final String abstractStr, final String startDate, final String endDate) {

		super.signIn("company1", "company1");

		super.clickOnMenu("My Company", "List Practicum");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(practicumRecord);
		super.checkFormExists();

		super.clickOnButton("List Sessions");
		super.checkListingExists();
		super.sortListing(2, "asc");

		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractStr", abstractStr);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);

		super.clickOnSubmit("Create");
		super.checkErrorsExist();
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		String param;
		Collection<Practicum> practicumCompany;

		practicumCompany = this.practicumRepository.findPracticumByCompanyName("company1");

		for (final Practicum practicum : practicumCompany) {
			param = String.format("masterId=%d", practicum.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/company/practicum-session/create", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
