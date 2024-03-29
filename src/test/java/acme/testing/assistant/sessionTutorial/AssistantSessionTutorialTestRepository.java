/*
 * AuthenticatedAnnouncementTestRepository.java
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

import org.springframework.data.jpa.repository.Query;

import acme.entities.tutorials.SessionTutorial;
import acme.entities.tutorials.Tutorial;
import acme.framework.repositories.AbstractRepository;

public interface AssistantSessionTutorialTestRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.assistant.userAccount.username != :username")
	Collection<Tutorial> findManyTutorialsOfOtherAssistants(final String username);

	@Query("select st from SessionTutorial st where st.tutorial.assistant.userAccount.username != :username")
	Collection<SessionTutorial> findManySessionTutorialsOfOtherAssistants(final String username);

	@Query("select st from SessionTutorial st where st.tutorial.assistant.userAccount.username = :username and st.tutorial.draft = true")
	Collection<SessionTutorial> findManyDraftSessionTutorialsByAssistantUsername(final String username);

	@Query("select st from SessionTutorial st where st.tutorial.assistant.userAccount.username = :username and st.tutorial.draft = false")
	Collection<SessionTutorial> findManyPublishedSessionTutorialsByAssistantUsername(final String username);

}
