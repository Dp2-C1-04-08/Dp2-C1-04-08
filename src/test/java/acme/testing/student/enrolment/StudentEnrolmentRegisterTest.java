
package acme.testing.student.enrolment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentEnrolmentRegisterTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String motivation, final String goals) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "List of courses");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.clickOnButton("Register");
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("motivation", motivation);
		super.fillInputBoxIn("goals", goals);

		super.clickOnSubmit("Register");

		super.clickOnMenu("Student", "List your enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(1);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("motivation", motivation);
		super.checkInputBoxHasValue("goals", goals);

		super.clickOnButton("Workbook");
		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code, final String motivation, final String goals) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "List of courses");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.clickOnButton("Register");
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("motivation", motivation);
		super.fillInputBoxIn("goals", goals);

		super.clickOnSubmit("Register");
		super.checkErrorsExist();
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/student/course/list");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/student/enrolment/register");
		super.checkPanicExists();
		super.signOut();
	}

}
