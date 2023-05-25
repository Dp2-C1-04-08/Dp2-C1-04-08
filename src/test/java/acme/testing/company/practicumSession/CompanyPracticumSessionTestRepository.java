
package acme.testing.company.practicumSession;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicumSessions.PracticumSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyPracticumSessionTestRepository extends AbstractRepository {

	@Query("SELECT ps FROM PracticumSession ps WHERE ps.practicum.title = :name")
	List<PracticumSession> findSessionsByPracticumTitle(String name);

	@Query("SELECT ps FROM PracticumSession ps WHERE ps.practicum.company.name= :name")
	List<PracticumSession> findSessionsByCompanyName(String name);

}
