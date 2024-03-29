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

package acme.testing.assistant.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.tutorials.Tutorial;
import acme.framework.repositories.AbstractRepository;

public interface AssistantTutorialTestRepository extends AbstractRepository {

	@Query("select t from Tutorial t where t.assistant.userAccount.username != :username")
	Collection<Tutorial> findManyTutorialsOfOtherAssistants(final String username);

	@Query("select t from Tutorial t where t.assistant.userAccount.username = :username and t.draft = true")
	Collection<Tutorial> findManyDraftTutorialsByAssistantUsername(final String username);

	@Query("select t from Tutorial t where t.assistant.userAccount.username = :username and t.draft = false")
	Collection<Tutorial> findManyPublishedTutorialsByAssistantUsername(final String username);

	@Query("select t from Tutorial t where t.assistant.userAccount.username = :username and t.draft = true and (select count(*) from SessionTutorial st where st.tutorial=t)>0")
	Collection<Tutorial> findManyPublicableTutorialsByAssistantUsername(final String username);
}
