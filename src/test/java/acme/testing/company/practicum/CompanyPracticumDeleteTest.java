
package acme.testing.company.practicum;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicums.Practicum;
import acme.testing.TestHarness;

public class CompanyPracticumDeleteTest extends TestHarness {

	@Autowired
	protected CompanyPracticumTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumRecordIndex) {

		super.signIn("company2", "company2");

		super.clickOnMenu("My Company", "List Practicum");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(practicumRecordIndex);
		super.checkFormExists();

		super.clickOnSubmit("Delete");

		super.clickOnMenu("My Company", "List Practicum");
		if (practicumRecordIndex == 0)
			super.checkListingEmpty();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int practicumRecordIndex) {
		super.signIn("company1", "company1");

		super.clickOnMenu("My Company", "List Practicum");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(practicumRecordIndex);
		super.checkFormExists();

		super.clickOnSubmit("Delete");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		String param;
		Collection<Practicum> practicumCompany;

		practicumCompany = this.repository.findPracticumByCompanyName("company1");
		for (final Practicum practicum : practicumCompany) {

			param = String.format("id=%d", practicum.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/company/practicum/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/company/practicum/delete", param);
			super.checkPanicExists();
			super.signOut();
		}

	}
}
