
package acme.features.authenticated.company.practicumSession;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicumSessions.PracticumSession;
import acme.entities.practicums.Practicum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyPracticumSessionRepository extends AbstractRepository {

	@Query("SELECT ps FROM PracticumSession ps WHERE ps.practicum.id = :id")
	List<PracticumSession> findAllSessionByPracticum(int id);

	@Query("SELECT p FROM Practicum p WHERE p.id = :id")
	Practicum findPracticumById(int id);

	@Query("SELECT ps FROM PracticumSession ps Where ps.id = :id")
	PracticumSession findSessionById(int id);

	@Query("SELECT COUNT(ps) FROM PracticumSession ps WHERE ps.practicum.id = :id AND ps.addendum=true")
	Integer countNumberOfAddendum(int id);
}
