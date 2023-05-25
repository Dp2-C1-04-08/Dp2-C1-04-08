
package acme.testing.student.enrolment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentEnrolmentShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String motivation, final String goals, final String course_code) {
		// HINT: this test signs in as an employer, then lists the announcements,
		// HINT+ and checks that the listing shows the expected data.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "List your enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("motivation", motivation);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("courseCode", course_code);
		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it
		// HINT+ doesn't involve any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show other assistant's tutorials not published
		// It does not take into account an authenticated triying to access a not published tutorial
		super.signIn("student2", "student2");

		super.signOut();

	}

}
