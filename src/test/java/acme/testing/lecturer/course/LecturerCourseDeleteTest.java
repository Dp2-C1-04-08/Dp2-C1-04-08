
package acme.testing.lecturer.course;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.courses.Course;
import acme.testing.TestHarness;

public class LecturerCourseDeleteTest extends TestHarness {

	@Autowired
	protected LecturerCourseTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int courseRecordIndex) {

		super.signIn("lecturer3", "lecturer3");

		super.clickOnMenu("Lecturer", "List Courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(courseRecordIndex);
		super.checkFormExists();

		super.clickOnSubmit("Delete");

		if (courseRecordIndex == 0) {
			super.clickOnMenu("Lecturer", "List Courses");
			super.checkListingEmpty();
		}
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int courseRecordIndex) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "List Courses");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(courseRecordIndex);
		super.checkFormExists();

		super.clickOnSubmit("Delete");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		String param;
		Collection<Course> courseLecturer;

		courseLecturer = this.repository.findCourseByLecturerResume("1");
		for (final Course course : courseLecturer) {

			param = String.format("id=%d", course.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/course/delete", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/lecturer/course/delete", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/course/delete", param);
			super.checkPanicExists();
			super.signOut();
		}

	}
}
