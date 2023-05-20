
package acme.testing.company.practicum;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.practicums.Practicum;
import acme.testing.TestHarness;

public class CompanyPracticumShowtTest extends TestHarness {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumRecordIndex, final String code, final String title, final String abstractStr, final String goals, final String published, final String course) {

		super.signIn("company1", "company1");

		super.clickOnMenu("My Company", "List Practicum");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(practicumRecordIndex);

		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("abstractStr", abstractStr);
		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("published", published);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there's no negative test case for this listing, since it doesn't
		// HINT+ involve filling in any forms.
	}

	@Test
	public void test300Hacking() {

		String param;
		Collection<Practicum> practicumCompany;

		practicumCompany = this.repository.findPracticumByCompanyName("company1");
		for (final Practicum practicum : practicumCompany) {

			param = String.format("id=%d", practicum.getId());

			super.checkLinkExists("Sign in");
			super.request("/company/practicum/show", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/company/practicum/show", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("company2", "company2");
			super.request("/company/practicum/show", param);
			super.checkPanicExists();
			super.signOut();

		}
	}

}
