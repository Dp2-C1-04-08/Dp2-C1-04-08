
package acme.testing.lecturer.course;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.courses.Course;
import acme.entities.courses.Nature;
import acme.framework.components.datatypes.Money;
import acme.testing.TestHarness;

public class LecturerCourseUpdateTest extends TestHarness {

	@Autowired
	protected LecturerCourseTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int courseRecordIndex, final String code, final String title, final String courseAbstract, final Nature courseType, final Money retailPrice, final String link, final boolean draft) {

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "List Courses");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(courseRecordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("courseAbstract", courseAbstract);
		super.fillInputBoxIn("retailPrice", retailPrice);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");

		super.clickOnMenu("lecturer", "List Courses");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.checkColumnHasValue(courseRecordIndex, 0, code);
		super.checkColumnHasValue(courseRecordIndex, 1, title);
		super.checkColumnHasValue(courseRecordIndex, 2, courseAbstract);

		super.clickOnListingRecord(courseRecordIndex);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("courseAbstract", courseAbstract);
		super.checkInputBoxHasValue("courseAbstract", courseAbstract);
		super.checkInputBoxHasValue("retailPrice", retailPrice);
		super.checkInputBoxHasValue("courseType", courseType);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("draft", draft);
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/course/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int courseRecordIndex, final String code, final String title, final String courseAbstract, final Nature courseType, final Money retailPrice, final String link, final boolean draft) {
		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "List Courses");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(courseRecordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("courseAbstract", courseAbstract);
		super.fillInputBoxIn("retailPrice", retailPrice);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		String param;
		Collection<Course> courseLecturer;

		courseLecturer = this.repository.findCourseByLecturerName("lecturer1");
		for (final Course course : courseLecturer) {

			param = String.format("id=%d", course.getId());

			super.checkLinkExists("Sign in");
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("lecturer2", "lecturer2");
			super.request("/lecturer/course/update", param);
			super.checkPanicExists();
			super.signOut();
		}

	}
}
