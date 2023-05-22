
package acme.testing.company.practicum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class CompanyPracticumCreateTest extends TestHarness {

	@Autowired
	protected CompanyPracticumTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int practicumRecordIndex, final String code, final String title, final String abstractStr, final String goals, final String published, final String course, final String estimatedTime, final String minus10estimatedTime,
		final String plus10estimatedTime) {

		super.signIn("company1", "company1");

		super.clickOnMenu("My Company", "Create Practicum");

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("abstractStr", abstractStr);

		super.fillInputBoxIn("course", course);

		super.clickOnSubmit("Create");

		super.clickOnMenu("My Company", "List Practicum");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.checkColumnHasValue(practicumRecordIndex, 0, code);
		super.checkColumnHasValue(practicumRecordIndex, 1, title);

		super.checkColumnHasValue(practicumRecordIndex, 2, published);
		super.checkColumnHasValue(practicumRecordIndex, 3, estimatedTime);

		super.clickOnListingRecord(practicumRecordIndex);

		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("abstractStr", abstractStr);
		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("published", published);
		super.checkInputBoxHasValue("minus10estimatedTime", minus10estimatedTime);

		super.checkInputBoxHasValue("plus10estimatedTime", plus10estimatedTime);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/company/practicum/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int practicumRecordIndex, final String code, final String title, final String abstractStr, final String goals, final String published, final String course, final String estimatedTime) {

		super.signIn("company1", "company1");

		super.clickOnMenu("My Company", "Create Practicum");

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("abstractStr", abstractStr);

		super.fillInputBoxIn("course", course);

		super.clickOnSubmit("Create");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/company/practicum/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/company/practicum/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer1", "lecturer1");
		super.request("/company/practicum/create");
		super.checkPanicExists();
		super.signOut();
	}

}
