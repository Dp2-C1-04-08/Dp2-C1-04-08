
package acme.testing.lecturer.lecture;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.courses.Lecture;
import acme.testing.TestHarness;

public class LecturerLectureListTest extends TestHarness {

	@Autowired
	protected LecturerLectureTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int lectureRecord, final int courseRecord, final String title, final String lectureAbstract, final String estimatedLearningTime) {

		super.signIn("company1", "company1");

		super.clickOnMenu("Lecturer", "List Courses");
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

		super.signOut();
	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {
		String param;
		List<Lecture> lectures;

		lectures = this.repository.findLectureByLecturerName("lecturer1");

		for (final Lecture lecture : lectures) {
			param = String.format("masterId=%d", 108);

			super.checkLinkExists("Sign in");
			super.request("/lecturer/lecture/list", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/lecturer/lecture/list", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/lecture/ist", param);
			super.checkPanicExists();
			super.signOut();
		}

	}

}
