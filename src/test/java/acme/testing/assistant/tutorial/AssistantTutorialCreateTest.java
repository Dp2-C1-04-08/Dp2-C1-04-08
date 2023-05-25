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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AssistantTutorialCreateTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	// Test methods -----------------------------------------------------------

	@ParameterizedTest
	@CsvFileSource(resources = "/assistant/tutorial/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String abstractStr, final String goals, final String estimatedTotalTime, final String draft, final String course) {
		// HINT: this test signs in as an employer, then lists the announcements,
		// HINT+ and checks that the listing shows the expected data.

		super.signIn("assistant1", "assistant1");
		super.clickOnMenu("Assistant", "My tutorials");

		super.checkListingExists();
		super.clickOnButton("Create");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("abstractStr", abstractStr);
		super.fillInputBoxIn("goals", goals);
		super.fillInputBoxIn("course", course);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Assistant", "My tutorials");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);

		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("abstractStr", abstractStr);
		super.checkInputBoxHasValue("goals", goals);
		super.checkInputBoxHasValue("course", course);
		super.checkInputBoxHasValue("estimatedTotalTime", "0.00");

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// HINT: there aren't any negative tests for this feature because it
		// HINT+ doesn't involve any forms.
	}

	//	@Test
	//	public void test300Hacking() {
	//		// HINT: this test tries to show old announcements as an authenticated principal.
	//
	//		Collection<Announcement> announcements;
	//		Date deadline;
	//		String query;
	//
	//		super.signIn("employer1", "employer1");
	//		deadline = MomentHelper.deltaFromMoment(MomentHelper.getBaseMoment(), -1, ChronoUnit.MONTHS);
	//		announcements = this.repository.findManyAnnouncementsBeforeDeadline(deadline);
	//		for (final Announcement announcement : announcements) {
	//			query = String.format("id=%d", announcement.getId());
	//			super.request("/authenticated/announcement/show", query);
	//			super.checkPanicExists();
	//		}
	//	}

}
