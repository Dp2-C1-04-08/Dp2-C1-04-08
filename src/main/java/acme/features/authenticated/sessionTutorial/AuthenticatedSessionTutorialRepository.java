/*
 * AuthenticatedAnnouncementRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.sessionTutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorials.SessionTutorial;
import acme.entities.tutorials.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedSessionTutorialRepository extends AbstractRepository {

	//	@Query("select t from Tutorial t where t.assistant.id = :assistantId")
	//	Collection<Tutorial> findManyTutorialsByAssistantId(int assistantId);

	@Query("select t from Tutorial t where t.id = :id")
	Tutorial findOneTutorialById(int id);

	@Query("select st.tutorial from SessionTutorial st where st.id = :id")
	Tutorial findOneTutorialBySessionTutorialId(int id);

	@Query("select st from SessionTutorial st where st.id = :id")
	SessionTutorial findOneSessionTutorialById(int id);

	//	@Query("select a from Assistant a where a.id = :id")
	//	Assistant findOneAssistantById(int id);
	//
	//	@Query("select c from Course c where c.draft = false")
	//	Collection<Course> findValidCourses();
	//
	//	@Query("select c from Course c where c.id = :id")
	//	Course findOneCourseById(int id);
	//
	//	@Query("select t from Tutorial t where t.code = :code")
	//	Optional<Tutorial> findTutorialByCode(String code);
	//
	//	@Query("select st from SessionTutorial st where st.tutorial.id = :id")
	//	Collection<SessionTutorial> getChildrenForTutorial(int id);

	@Query("select st from SessionTutorial st where st.tutorial.id = :masterId")
	Collection<SessionTutorial> findManySessionsTutorialByMasterId(int masterId);
}
