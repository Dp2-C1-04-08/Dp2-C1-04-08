
package acme.testing.student.enrolment;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.enrolments.Enrolment;
import acme.testing.TestHarness;

public class StudentEnrolmentFinaliseTest extends TestHarness {

	@Autowired
	protected StudentEnrolmentTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/finalise-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String CCH, final String expDate, final String cvc, final String upperNibble, final String lowerNibble, final String isFinalised) {

		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "List your enrolments");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnButton("Finalise");
		super.checkFormExists();

		super.fillInputBoxIn("creditCardHolder", CCH);
		super.fillInputBoxIn("expiryDate", expDate);
		super.fillInputBoxIn("cvc", cvc);
		super.fillInputBoxIn("upperNibble", upperNibble);
		super.fillInputBoxIn("lowerNibble", lowerNibble);

		super.clickOnSubmit("Finalise");

		super.clickOnMenu("Student", "List your enrolments");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.checkColumnHasValue(recordIndex, 2, isFinalised);
		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/student/enrolment/finalise-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String CCH, final String expDate, final String cvc, final String upperNibble, final String lowerNibble) {
		super.signIn("student1", "student1");

		super.clickOnMenu("Student", "List your enrolments");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(recordIndex + 1);
		super.checkFormExists();
		super.clickOnButton("Finalise");
		super.checkFormExists();

		super.fillInputBoxIn("creditCardHolder", CCH);
		super.fillInputBoxIn("expiryDate", expDate);
		super.fillInputBoxIn("cvc", cvc);
		super.fillInputBoxIn("upperNibble", upperNibble);
		super.fillInputBoxIn("lowerNibble", lowerNibble);

		super.clickOnSubmit("Finalise");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {

		String param;
		final Collection<Enrolment> enrolments;

		enrolments = this.repository.findEnrolmentsByStudentId(1);
		for (final Enrolment enrolment : enrolments) {

			param = String.format("id=%d", enrolment.getId());

			super.checkLinkExists("Sign in");
			super.request("/student/enrolment/finalise", param);
			super.checkPanicExists();

			super.signIn("administrator", "administrator");
			super.request("/student/enrolment/finalise", param);
			super.checkPanicExists();
			super.signOut();

			super.signIn("student2", "student2");
			super.request("/student/enrolment/finalise", param);
			super.checkPanicExists();
			super.signOut();

		}
	}
}
