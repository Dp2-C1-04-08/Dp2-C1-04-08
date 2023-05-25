
package acme.testing.lecturer.lecture;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.courses.Lecture;
import acme.testing.TestHarness;

public class LecturerLectureDeleteTest extends TestHarness {

	@Autowired
	protected LecturerLectureTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int lectureRecord, final int courseRecord) {

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "List Courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(courseRecord);
		super.checkFormExists();

		super.clickOnButton("List Lecture");
		super.checkListingExists();
		super.sortListing(2, "asc");

		super.clickOnListingRecord(lectureRecord);
		super.checkFormExists();

		super.clickOnSubmit("Delete");

		if (lectureRecord == 0) {
			super.clickOnMenu("Lecturer", "List Courses");
			super.checkListingExists();
			super.sortListing(0, "asc");

			super.clickOnListingRecord(courseRecord);
			super.checkFormExists();

			super.clickOnButton("List Lecture");
			super.checkListingEmpty();
		}

		super.signOut();
	}

	@Test
	public void test200Negative() {
	}

	@Test
	public void test300Hacking() {
		String param;
		Collection<Lecture> lectures;

		lectures = this.repository.findLectureByLecturerResume("1");

		for (final Lecture lecture : lectures) {
			param = String.format("masterId=%d", lecture.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/lecture/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/lecturer/lecture/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/lecture/delete", param);
			super.checkPanicExists();
			super.signOut();
		}
	}
}
