
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.courses.Lecture;
import acme.testing.TestHarness;

public class LecturerLecturePublishTest extends TestHarness {

	// Internal data ----------------------------------------------------------

	@Autowired
	protected LecturerLectureTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int lectureRecord, final int courseRecord, final String title) {

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "List Courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(courseRecord);
		super.checkFormExists();

		super.clickOnButton("List Lecture");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(lectureRecord, 0, title);

		super.clickOnListingRecord(lectureRecord);

		super.checkFormExists();

		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there's no negative test case for this listing, since it doesn't
		// HINT+ involve filling in any forms.
	}

	@Test
	public void test300Hacking() {

		Collection<Lecture> lectures;
		String params;

		lectures = this.repository.findLectureByLecturerResume("1");
		for (final Lecture lecture : lectures)
			if (lecture.isDraft()) {
				params = String.format("id=%d", lecture.getId());

				super.checkLinkExists("Sign in");
				super.request("/lecturer/lecture/publish", params);
				super.checkPanicExists();

				super.signIn("administrator", "administrator");
				super.request("/lecturer/lecture/publish", params);
				super.checkPanicExists();
				super.signOut();

				super.signIn("company1", "company1");
				super.request("/lecturer/lecture/publish", params);
				super.checkPanicExists();
				super.signOut();
			}
	}

}
