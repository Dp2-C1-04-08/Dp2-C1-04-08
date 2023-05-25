
package acme.testing.any.peep;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyPeepListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/peep/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String instanciationMoment, final String nick, final String title) {

		super.clickOnMenu("Any", "Peep");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, instanciationMoment);
		super.checkColumnHasValue(recordIndex, 1, nick);
		super.checkColumnHasValue(recordIndex, 2, title);
	}

	@Test
	public void test200Negative() {
		//There is no negative test case
	}

	public void test300Hacking() {

	}

}
