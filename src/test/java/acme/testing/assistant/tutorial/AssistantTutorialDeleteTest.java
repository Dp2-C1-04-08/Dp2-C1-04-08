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

package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorials.Tutorial;
import acme.testing.TestHarness;

public class AssistantTutorialDeleteTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	AssistantTutorialTestRepository repository;
	// Test methods -----------------------------------------------------------


	@Test
	public void test100Positive() {
		// HINT: this test signs in as an employer, then lists the announcements,
		// HINT+ and checks that the listing shows the expected data.

		super.signIn("assistant1", "assistant1");
		final Collection<Tutorial> deleteableTutorials = this.repository.findManyDraftTutorialsByAssistantUsername("assistant1");

		for (final Tutorial tutorial : deleteableTutorials) {
			final String query = String.format("id=%d", tutorial.getId());
			super.request("/assistant/tutorial/show", query);
			super.checkFormExists();
			super.clickOnSubmit("Delete");
			super.request("/assistant/tutorial/show", query);
			super.checkPanicExists();
		}
		super.signOut();
	}

	@Test
	public void test200Negative() {
		super.signIn("assistant1", "assistant1");
		final Collection<Tutorial> publishedTutorials = this.repository.findManyPublishedTutorialsByAssistantUsername("assistant1");
		for (final Tutorial tutorial : publishedTutorials) {
			final String query = String.format("id=%d", tutorial.getId());
			super.request("/assistant/tutorial/show", query);
			super.checkFormExists();
			super.checkNotSubmitExists("Delete");
		}
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show other assistant's tutorials not published
		// It does not take into account an authenticated triying to access a not published tutorial

		final Collection<Tutorial> tutorials = this.repository.findManyTutorialsOfOtherAssistants("assistant2");
		for (final Tutorial tutorial : tutorials) {
			final String query = String.format("id=%d", tutorial.getId());
			super.checkLinkExists("Sign in");
			super.request("/assistant/tutorial/delete", query);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/assistant/tutorial/delete", query);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant2", "assistant2");
			super.request("/assistant/tutorial/delete", query);
			super.checkPanicExists();
			super.signOut();
		}

	}

}
