
package acme.testing.student.activity;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolments.Activity;
import acme.testing.TestHarness;

public class StudentActivityDeleteTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentActivityTestRepository repository;


	@Test
	public void test100Positive() {
		// HINT: this test signs in as an employer, then lists the announcements,
		// HINT+ and checks that the listing shows the expected data.

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "List your enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);
		super.checkFormExists();

		super.clickOnButton("Workbook");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);
		super.checkFormExists();

		final String url = super.getCurrentPath();
		super.clickOnSubmit("Delete");

		super.request(url);
		super.checkPanicExists();

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
		String param;
		final Collection<Activity> activities;

		activities = this.repository.findActivitiesByEnrolmentId(1);
		for (final Activity activity : activities) {

			param = String.format("id=%d", activity.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/activity/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/student/activity/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student2", "student2");
			super.request("/student/activity/delete", param);
			super.checkPanicExists();
			super.signOut();

		}
	}
}
