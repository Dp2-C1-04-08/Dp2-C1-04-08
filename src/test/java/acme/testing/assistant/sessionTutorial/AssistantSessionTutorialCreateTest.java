/*
 * AuthenticatedAnnnouncementShowTest.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.testing.assistant.sessionTutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorials.Tutorial;
import acme.testing.TestHarness;

public class AssistantSessionTutorialCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	AssistantSessionTutorialTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session-tutorial/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int tutorialRecordIndex, final String title, final String abstractStr, final String type, final String start, final String end, final String tutorialCode, final String link) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(tutorialRecordIndex, 0, tutorialCode);
		super.clickOnListingRecord(tutorialRecordIndex);
		super.checkInputBoxHasValue("code", tutorialCode);

		super.clickOnButton("Sessions");
		super.checkListingExists();
		super.clickOnButton("Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractStr", abstractStr);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("startTime", start);
		super.fillInputBoxIn("endTime", end);
		super.clickOnSubmit("Create");
		super.checkNotErrorsExist();

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(tutorialRecordIndex, 0, tutorialCode);
		super.clickOnListingRecord(tutorialRecordIndex);
		super.checkInputBoxHasValue("code", tutorialCode);

		super.clickOnButton("Sessions");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractStr", abstractStr);
		super.checkInputBoxHasValue("type", type);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("startTime", start);
		super.checkInputBoxHasValue("endTime", end);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session-tutorial/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final int tutorialRecordIndex, final String title, final String abstractStr, final String type, final String start, final String end, final String tutorialCode, final String link) {
		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(tutorialRecordIndex, 0, tutorialCode);
		super.clickOnListingRecord(tutorialRecordIndex);
		super.checkInputBoxHasValue("code", tutorialCode);

		super.clickOnButton("Sessions");
		super.checkListingExists();
		super.clickOnButton("Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstractStr", abstractStr);
		super.fillInputBoxIn("type", type);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("startTime", start);
		super.fillInputBoxIn("endTime", end);
		super.clickOnSubmit("Create");
		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		final Collection<Tutorial> tutorials = this.repository.findManyTutorialsOfOtherAssistants("assistant2");
		for (final Tutorial tutorial : tutorials) {
			final String query = String.format("id=%d", tutorial.getId());
			super.signOut();
			super.checkLinkExists("Sign in");
			super.request("/assistant/session-tutorial/create", query);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/assistant/session-tutorial/create", query);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant2", "assistant2");
			super.request("/assistant/session-tutorial/create", query);
			super.checkPanicExists();
			super.signOut();
		}

	}

}
