/*
 * AuthenticatedAnnouncementListTest.java
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

public class AssistantSessionTutorialListTest extends TestHarness {

	@Autowired
	AssistantSessionTutorialTestRepository repository;


	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/session-tutorial/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final int tutorialRecordIndex, final String title, final String abstractStr, final String type, final String start, final String tutorialCode) {
		// HINT: this test authenticates as an employer and checks that he or
		// HINT+ she can display the expected announcements.

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

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there's no negative test case for this listing, since it doesn't
		// HINT+ involve filling in any forms.
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to request the list of announcements as
		// HINT+ an anonymous principal.

		super.signIn("assistant2", "assistant2");

		final Collection<Tutorial> tutorials = this.repository.findManyTutorialsOfOtherAssistants("assistant2");
		for (final Tutorial tutorial : tutorials) {
			final String query = String.format("masterId=%d", tutorial.getId());
			super.request("/assistant/session-tutorial/list", query);
			super.checkPanicExists();
		}

		super.signOut();

	}

	// Ancillary methods ------------------------------------------------------

}
