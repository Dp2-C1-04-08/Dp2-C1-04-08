
package acme.features.auditor.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(a) FROM Audit a WHERE a.auditor.id = :id AND a.course.courseType = acme.entities.courses.Nature.THEORETICAL")
	Integer numberOfAuditsForTheoreticalCourses(int id);

	@Query("SELECT COUNT(a) FROM Audit a WHERE a.auditor.id = :id AND a.course.courseType = acme.entities.courses.Nature.HANDS_ON")
	Integer numberOfAuditsForHandsOnCourses(int id);

	@Query("SELECT avg(SELECT COUNT(ar) FROM AuditingRecord ar WHERE ar.audit.id = a.id) FROM Audit a WHERE a.auditor.id = :id")
	Double averageNumberOfRecordsInAudit(int id);

	@Query("SELECT min(SELECT COUNT(ar) FROM AuditingRecord ar WHERE ar.audit.id = a.id) FROM Audit a WHERE a.auditor.id = :id")
	Double minimumNumberOfRecordsInAudit(int id);

	@Query("SELECT max(SELECT COUNT(ar) FROM AuditingRecord ar WHERE ar.audit.id = a.id) FROM Audit a WHERE a.auditor.id = :id")
	Double maximumNumberOfRecordsInAudit(int id);

	@Query("SELECT a FROM Audit a WHERE a.auditor.id = :id")
	Collection<Audit> findAllAuditsForAuditor(int id);

	@Query("SELECT COUNT(ar) FROM AuditingRecord ar WHERE ar.audit.id = :id")
	Integer countRecordsInAudit(int id);

	@Query("SELECT ar FROM AuditingRecord ar WHERE ar.audit.auditor.id = :id")
	Collection<AuditingRecord> findAllRecordsForAuditor(int id);

	@Query("SELECT COUNT(ar) FROM AuditingRecord ar WHERE ar.audit.auditor.id = :id")
	Integer countRecordsForAuditor(int id);

	@Query("SELECT COUNT(a) FROM Audit a WHERE a.auditor.id = :id")
	Integer countAuditsForAuditor(int id);

}
