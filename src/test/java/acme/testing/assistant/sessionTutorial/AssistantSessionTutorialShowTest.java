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

import acme.entities.tutorials.SessionTutorial;
import acme.testing.TestHarness;

public class AssistantSessionTutorialShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	AssistantSessionTutorialTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session-tutorial/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int tutorialRecordIndex, final String title, final String abstractStr, final String type, final String start, final String end, final String tutorialCode, final String link, final String draft) {

		super.signIn("assistant1", "assistant1");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(tutorialRecordIndex, 0, tutorialCode);
		super.clickOnListingRecord(tutorialRecordIndex);
		super.checkInputBoxHasValue("code", tutorialCode);
		super.clickOnButton("Sessions");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, abstractStr);
		super.checkColumnHasValue(recordIndex, 2, type);
		super.checkColumnHasValue(recordIndex, 3, start);
		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("type", type);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstractStr", abstractStr);
		super.checkInputBoxHasValue("startTime", start);
		super.checkInputBoxHasValue("endTime", end);
		super.checkInputBoxHasValue("link", link);
		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it
		// HINT+ doesn't involve any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show other assistant's sessiontutorials
		// It does not take into account an authenticated triying to access a not published tutorial
		super.signIn("assistant2", "assistant2");

		final Collection<SessionTutorial> sessions = this.repository.findManySessionTutorialsOfOtherAssistants("assistant2");
		for (final SessionTutorial session : sessions) {
			final String query = String.format("id=%d", session.getId());
			super.request("/assistant/session-tutorial/show", query);
			super.checkPanicExists();
		}

		super.signOut();

	}

}
