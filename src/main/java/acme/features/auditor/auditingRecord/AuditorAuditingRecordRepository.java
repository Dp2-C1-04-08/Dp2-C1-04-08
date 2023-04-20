
package acme.features.auditor.auditingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditingRecordRepository extends AbstractRepository {

	@Query("SELECT a FROM Audit a WHERE a.id = :auditId")
	Audit findOneAuditById(int auditId);

	@Query("SELECT ar FROM AuditingRecord ar WHERE ar.audit.id = :masterId")
	Collection<AuditingRecord> findManyAuditingRecordsByMasterId(int masterId);

	@Query("SELECT ar.audit FROM AuditingRecord ar WHERE ar.id = :id")
	Audit findOneAuditByAuditingRecordId(int id);

	@Query("SELECT ar FROM AuditingRecord ar WHERE ar.id = :id")
	AuditingRecord findOneAuditingRecordById(int id);

}
