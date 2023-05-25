
package acme.testing.lecturer.courseLecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class LecturerCourseLectureCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course-lecture/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int courseRecordIndex, final int lectureRecordIndex, final String course, final String lecture, final String title, final String lectureAbstract, final String estimatedLearningTime) {

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "Add Lecture to Course");

		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("lecture", lecture);

		super.clickOnSubmit("Add");

		super.clickOnMenu("Lecturer", "List Courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(courseRecordIndex);
		super.checkFormExists();

		super.clickOnButton("List Lecture");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(lectureRecordIndex, 0, title);
		super.checkColumnHasValue(lectureRecordIndex, 1, lectureAbstract);
		super.checkColumnHasValue(lectureRecordIndex, 2, estimatedLearningTime);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course-lecture/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final String course, final String lecture) {

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "Add Lecture to Course");

		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("lecture", lecture);

		super.clickOnSubmit("Add");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/lecturer/course-lecture/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/course-lecture/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/lecturer/course-lecture/create");
		super.checkPanicExists();
		super.signOut();
	}

}
