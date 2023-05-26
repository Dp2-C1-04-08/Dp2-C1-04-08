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
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.tutorials.SessionTutorial;
import acme.testing.TestHarness;

public class AssistantSessionTutorialDeleteTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	AssistantSessionTutorialTestRepository repository;

	// Test methods -----------------------------------------------------------


	@Test
	public void test100Positive() {
		// HINT: this test signs in as an employer, then lists the announcements,
		// HINT+ and checks that the listing shows the expected data.

		super.signIn("assistant1", "assistant1");
		final Collection<SessionTutorial> deleteableSessionTutorials = this.repository.findManyDraftSessionTutorialsByAssistantUsername("assistant1");

		for (final SessionTutorial session : deleteableSessionTutorials) {
			final String query = String.format("id=%d", session.getId());
			super.request("/assistant/session-tutorial/show", query);
			super.checkFormExists();
			super.clickOnSubmit("Delete");
			super.request("/assistant/session-tutorial/show", query);
			super.checkPanicExists();
		}
		super.signOut();
	}

	@Test
	public void test200Negative() {
		super.signIn("assistant1", "assistant1");
		final Collection<SessionTutorial> publishedSessionTutorials = this.repository.findManyPublishedSessionTutorialsByAssistantUsername("assistant1");
		for (final SessionTutorial session : publishedSessionTutorials) {
			final String query = String.format("id=%d", session.getId());
			super.request("/assistant/session-tutorial/show", query);
			super.checkFormExists();
			super.checkNotSubmitExists("Delete");
		}
		super.signOut();
	}

	@Test
	public void test300Hacking() {
		// HINT: this test tries to show other assistant's tutorials not published
		// It does not take into account an authenticated triying to access a not published tutorial

		final Collection<SessionTutorial> sessions = this.repository.findManySessionTutorialsOfOtherAssistants("assistant2");
		for (final SessionTutorial session : sessions) {
			final String query = String.format("id=%d", session.getId());
			super.checkLinkExists("Sign in");
			super.request("/assistant/session-tutorial/delete", query);
			super.checkPanicExists();

			super.signIn("administrator1", "administrator1");
			super.request("/assistant/session-tutorial/delete", query);
			super.checkPanicExists();
			super.signOut();

			super.signIn("assistant2", "assistant2");
			super.request("/assistant/tutorial/delete", query);
			super.checkPanicExists();
			super.signOut();
		}

	}

}
