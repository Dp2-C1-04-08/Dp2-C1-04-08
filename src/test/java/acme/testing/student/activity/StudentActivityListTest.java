//
//package acme.testing.student.activity;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//
//import acme.testing.TestHarness;
//
//public class StudentActivityListTest extends TestHarness {
//
//	@ParameterizedTest
//	@CsvFileSource(resources = "/student/activity/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
//	public void test100Positive(final int recordIndex, final String title, final String activityAbstract, final String type) {
//		// HINT: this test authenticates as an employer and checks that he or
//		// HINT+ she can list his or her applications.
//
//		super.signIn("student1", "student1");
//
//		super.clickOnMenu("Student", "List your enrolments");
//		super.checkListingExists();
//		super.sortListing(0, "asc");
//
//		super.clickOnListingRecord(0);
//		super.checkFormExists();
//
//		super.clickOnButton("Workbook");
//		super.checkListingExists();
//		super.sortListing(2, "asc");
//
//		super.checkColumnHasValue(recordIndex, 0, title);
//		super.checkColumnHasValue(recordIndex, 1, activityAbstract);
//		super.checkColumnHasValue(recordIndex, 2, type);
//
//		super.signOut();
//	}
//
//	@Test
//	public void test200Negative() {
//		// HINT: this is a listing, which implies that no data must be entered in any forms.
//		// HINT+ Then, there are not any negative test cases for this feature.
//	}
//
//	@Test
//	public void test300Hacking() {
//		// HINT: this test tries to list the applications of an employer as a
//		// HINT+ principal with the wrong role.
//
//		super.checkLinkExists("Sign in");
//		super.request("/student/activity/list");
//		super.checkPanicExists();
//
//		super.checkLinkExists("Sign in");
//		super.signIn("administrator1", "administrator1");
//		super.request("/student/activity/list");
//		super.checkPanicExists();
//		super.signOut();
//	}
//}
