
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolments.Activity;
import acme.testing.TestHarness;

public class StudentActivityUpdateTest extends TestHarness {

	@Autowired
	protected StudentActivityTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String activityAbstract, final String type, final String start, final String end, final String link) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "List your enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.clickOnButton("Workbook");

		super.checkListingExists();
		super.sortListing(2, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("activityAbstract", activityAbstract);
		super.fillInputBoxIn("activityType", type);
		super.fillInputBoxIn("startTime", start);
		super.fillInputBoxIn("endTime", end);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");

		super.clickOnMenu("Student", "List your enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.clickOnButton("Workbook");

		super.checkListingExists();
		super.sortListing(2, "asc");

		super.clickOnListingRecord(0);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("activityAbstract", activityAbstract);
		super.checkInputBoxHasValue("activityType", type);
		super.checkInputBoxHasValue("startTime", start);
		super.checkInputBoxHasValue("endTime", end);
		super.checkInputBoxHasValue("link", link);

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String activityAbstract, final String type, final String start, final String end, final String link) {
		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "List your enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.clickOnButton("Workbook");

		super.checkListingExists();
		super.sortListing(2, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("activityAbstract", activityAbstract);
		super.fillInputBoxIn("activityType", type);
		super.fillInputBoxIn("startTime", start);
		super.fillInputBoxIn("endTime", end);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		// HINT: this test tries to show other assistant's tutorials not published
		// It does not take into account an authenticated triying to access a not published tutorial
		String param;
		final Collection<Activity> activities;

		activities = this.repository.findActivitiesByEnrolmentId(1);
		for (final Activity activity : activities) {

			param = String.format("id=%d", activity.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/enrolment/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/student/enrolment/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student2", "student2");
			super.request("/student/enrolment/update", param);
			super.checkPanicExists();
			super.signOut();

		}

	}
}
