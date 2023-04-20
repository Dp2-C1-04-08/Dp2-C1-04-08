
package acme.features.assistant.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorials.SessionTutorial;
import acme.entities.tutorials.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantDashboardRepository extends AbstractRepository {

	// tutorials
	@Query("select avg(t.estimatedTotalTime) from Tutorial t where t.assistant.id = :id")
	Double averageDurationTutorial(int id);

	@Query("select STDDEV(t.estimatedTotalTime) from Tutorial t where t.assistant.id = :id")
	Double deviationDuractionTutorial(int id);

	@Query("select min(t.estimatedTotalTime)from Tutorial t where t.assistant.id = :id")
	Double minDurationTutorial(int id);

	@Query("select max(t.estimatedTotalTime) from Tutorial t where t.assistant.id = :id")
	Double maxDurationTutorial(int id);

	@Query("select t from Tutorial t where t.assistant.id = :id")
	Collection<Tutorial> findTutorialsByAssistantId(int id);

	@Query("select st from SessionTutorial st where st.tutorial.assistant.id = :id")
	Collection<SessionTutorial> findSessionTutorialByAssistantId(int id);

}
