
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audits.Audit;
import acme.entities.courses.Course;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditRepository extends AbstractRepository {

	@Query("SELECT a FROM Audit a WHERE a.auditor.id = :auditorId")
	Collection<Audit> findManyAuditsByAuditorId(int auditorId);

	@Query("SELECT COUNT(a) FROM Audit a WHERE a.id = :auditId AND a.auditor.id = :auditorId")
	Integer existsAuditWithIdForAuditor(int auditId, int auditorId);

	@Query("SELECT a FROM Audit a WHERE a.id = :auditId")
	Audit findOneAuditById(int auditId);

	@Query("SELECT c FROM Course c")
	Collection<Course> findAllCourses();

	@Query("SELECT c FROM Course c WHERE c.draft = false")
	Collection<Course> findValidCourses();

}
