
package acme.features.auditor.dashboard;

import java.util.Collection;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("SELECT a.course.courseType, COUNT(a) FROM Audit a WHERE a.auditor.id = :id GROUP BY a.course.courseType")
	Collection<Tuple> numberOfAuditsForCoursesByType(int id);

	@Query("SELECT avg(COUNT(ar)) FROM AuditingRecord ar WHERE ar.audit.auditor.id = :id")
	Double averageNumberOfRecordsInAudit(int id);

	@Query("SELECT STDDEV(COUNT(ar)) FROM AuditingRecord ar WHERE ar.audit.auditor.id = :id")
	Double deviationNumberOfRecordsInAudit(int id);

	@Query("SELECT min(COUNT(ar)) FROM AuditingRecord ar WHERE ar.audit.auditor.id = :id")
	Double minimumNumberOfRecordsInAudit(int id);

	@Query("SELECT max(COUNT(ar)) FROM AuditingRecord ar WHERE ar.audit.auditor.id = :id")
	Double maximumNumberOfRecordsInAudit(int id);

}
