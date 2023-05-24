
package acme.testing.any.peep;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyPeepCreateTest extends TestHarness {

	public void test100Positive() {

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int peepRecordIndex, final String title, final String nick, final String message, final String email, final String link) {
		//This test attempts to create peeps using wrong data.

		super.requestHome();

		super.clickOnMenu("Any", "Peep");
		super.checkListingExists();

		super.clickOnButton("Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("nick", nick);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test201Negative(final int peepRecordIndex, final String title, final String nick, final String message, final String email, final String link) {
		//This test attempts to create peeps using wrong data while being signed in as auditor.

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Any", "Peep");
		super.checkListingExists();

		super.clickOnButton("Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("nick", nick);
		super.fillInputBoxIn("message", message);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();

		super.signOut();
	}

	public void test300Hacking() {

	}

}
