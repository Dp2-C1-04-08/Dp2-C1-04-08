
package acme.features.auditor.audit;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.entities.courses.Course;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Auditor;

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

	@Query("SELECT a FROM Auditor a WHERE a.id = :auditorId")
	Auditor findOneAuditorById(int auditorId);

	@Query("SELECT c FROM Course c WHERE c.id = :id")
	Course findOneCourseById(int id);

	@Query("SELECT a FROM Audit a WHERE a.code = :code")
	Optional<Audit> findAuditByCode(String code);

	@Query("SELECT ar FROM AuditingRecord ar WHERE ar.audit.id = :id")
	Collection<AuditingRecord> getRecordsForAudit(int id);

}
