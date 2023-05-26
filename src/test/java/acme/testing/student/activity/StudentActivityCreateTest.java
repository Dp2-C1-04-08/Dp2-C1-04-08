
package acme.testing.student.activity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class StudentActivityCreateTest extends TestHarness {

	//	@Autowired
	//	protected CompanyPracticumSessionTestRepository	repository;
	//
	//	@Autowired
	//	protected CompanyPracticumTestRepository		practicumRepository;

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String activityAbstract, final String type, final String start, final String end, final String link) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "List your enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.clickOnButton("Workbook");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.checkFormExists();

		//		title,activityAbstract,type,start,end,link
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("activityAbstract", activityAbstract);
		super.fillInputBoxIn("activityType", type);
		super.fillInputBoxIn("startTime", start);
		super.fillInputBoxIn("endTime", end);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Create");

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

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/activity/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String activityAbstract, final String type, final String start, final String end, final String link) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "List your enrolments");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);
		super.checkFormExists();
		super.clickOnButton("Workbook");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.checkFormExists();

		//		title,activityAbstract,type,start,end,link
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("activityAbstract", activityAbstract);
		super.fillInputBoxIn("activityType", type);
		super.fillInputBoxIn("startTime", start);
		super.fillInputBoxIn("endTime", end);
		super.fillInputBoxIn("link", link);

		super.clickOnSubmit("Create");
		super.checkErrorsExist();
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/student/activity/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/student/activity/create");
		super.checkPanicExists();
		super.signOut();
	}

}
