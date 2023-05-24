
package acme.testing.lecturer.lecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.testing.TestHarness;

public class LecturerLectureShowTest extends TestHarness {

	@Autowired
	protected LecturerLectureTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int courseRecord, final int lectureRecord, final String title, final String lectureAbstract, final String link, final String estimatedLearningTime, final String body, final String lectureType) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "List Lecturer");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(courseRecord);
		super.checkFormExists();

		super.clickOnButton("List Lecture");
		super.checkListingExists();
		super.sortListing(2, "asc");

		super.checkColumnHasValue(lectureRecord, 0, title);
		super.checkColumnHasValue(lectureRecord, 1, lectureAbstract);
		super.checkColumnHasValue(lectureRecord, 2, estimatedLearningTime);

		super.clickOnListingRecord(lectureRecord);
		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("lectureAbstract", lectureAbstract);
		super.checkInputBoxHasValue("estimatedLearningTime", estimatedLearningTime);
		super.checkInputBoxHasValue("body", body);
		super.checkInputBoxHasValue("lectureType", lectureType);
		super.checkInputBoxHasValue("link", link);

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

		param = String.format("masterId=%d", 108);

		super.checkLinkExists("Sign in");
		super.request("/lecturer/lecture/show", param);
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/lecture/show", param);
		super.checkPanicExists();
		super.signOut();

		super.signIn("lecturer2", "lecturer2");
		super.request("/lecturer/lecture/show", param);
		super.checkPanicExists();
		super.signOut();
	}
}
