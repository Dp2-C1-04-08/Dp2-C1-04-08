
package acme.testing.auditor.auditingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.audits.Audit;
import acme.entities.audits.AuditingRecord;
import acme.framework.repositories.AbstractRepository;

public interface AuditorAuditingRecordTestRepository extends AbstractRepository {

	@Query("SELECT a FROM Audit a WHERE a.auditor.userAccount.username != :username")
	Collection<Audit> findManyAuditsOfOtherAuditors(final String username);

	@Query("SELECT ar FROM AuditingRecord ar WHERE ar.audit.code = :code")
	Collection<AuditingRecord> findRecordsByAuditCode(String code);
}
