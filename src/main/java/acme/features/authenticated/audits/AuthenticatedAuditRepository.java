
package acme.features.authenticated.audits;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audits.Audit;
import acme.entities.courses.Course;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedAuditRepository extends AbstractRepository {

	@Query("SELECT a FROM Audit a WHERE a.published = true AND a.course.id = :id")
	Collection<Audit> findAvailableAudits(int id);

	@Query("SELECT a FROM Audit a WHERE a.id = :id")
	Audit findOneAuditById(int id);

	@Query("SELECT c FROM Course c WHERE c.id = :id")
	Course findOneCourseById(int id);

}
