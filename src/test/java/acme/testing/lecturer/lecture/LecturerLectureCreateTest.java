
package acme.testing.lecturer.lecture;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.courses.Nature;
import acme.testing.TestHarness;
import acme.testing.lecturer.course.LecturerCourseTestRepository;

public class LecturerLectureCreateTest extends TestHarness {

	@Autowired
	protected LecturerLectureTestRepository	repository;

	@Autowired
	protected LecturerCourseTestRepository	courseRepository;


	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int lectureRecord, final String title, final String lectureAbstract, final String link, final Double estimatedLearningTime, final String body, final Nature lectureType, final boolean draft) {

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "Create Lecture");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("lectureAbstract", lectureAbstract);
		super.fillInputBoxIn("estimatedLearningTime", estimatedLearningTime);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("lectureType", lectureType);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Create");

		super.clickOnMenu("Lecturer", "Add Lecture to Course");
		super.fillInputBoxIn("course", course);
		super.fillInputBoxIn("lecture", lecture);

		super.clickOnSubmit("Add");

		super.clickOnMenu("Lecturer", "List Courses");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(courseRecord);
		super.checkFormExists();

		super.clickOnButton("List Lecture");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(lectureRecord);

		super.checkFormExists();

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("lectureAbstract", lectureAbstract);
		super.checkInputBoxHasValue("estimatedLearningTime", estimatedLearningTime);
		super.checkInputBoxHasValue("body", body);
		super.checkInputBoxHasValue("lectureType", lectureType);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("draft", draft);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/lecturer/lecture/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int lectureRecord, final String title, final String lectureAbstract, final String link, final Double estimatedLearningTime, final String body, final Nature lectureType, final boolean draft) {

		super.signIn("lecturer1", "lecturer1");

		super.clickOnMenu("Lecturer", "Create Lecture");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("lectureAbstract", lectureAbstract);
		super.fillInputBoxIn("estimatedLearningTime", estimatedLearningTime);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("lectureType", lectureType);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Create");
		super.checkErrorsExist();
		super.signOut();
	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/lecturer/lecture/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/lecturer/lecture/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("company1", "company1");
		super.request("/lecturer/lecture/create");
		super.checkPanicExists();
		super.signOut();
	}
}
