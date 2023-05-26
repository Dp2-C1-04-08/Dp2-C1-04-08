
package acme.testing.company.practicum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class CompanyPracticumListTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected CompanyPracticumTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumRecordIndex, final String title, final String code, final String published, final String estimatedTime) {

		super.signIn("company1", "company1");

		super.clickOnMenu("My Company", "List Practicum");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.checkColumnHasValue(practicumRecordIndex, 0, title);
		super.checkColumnHasValue(practicumRecordIndex, 1, code);

		super.checkColumnHasValue(practicumRecordIndex, 2, published);
		super.checkColumnHasValue(practicumRecordIndex, 3, estimatedTime);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there's no negative test case for this listing, since it doesn't
		// HINT+ involve filling in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to list the duties of a job that is unpublished
		// HINT+ using a principal that didn't create it. 

		super.checkLinkExists("Sign in");
		super.request("/company/practicum/list");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/company/practicum/list");
		super.checkPanicExists();
		super.signOut();

	}

}
