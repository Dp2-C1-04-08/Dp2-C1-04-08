
package acme.testing.auditor.audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.audits.Audit;
import acme.framework.repositories.AbstractRepository;

public interface AuditorAuditTestRepository extends AbstractRepository {

	@Query("SELECT a FROM Audit a WHERE a.auditor.userAccount.username != :username")
	Collection<Audit> findManyAuditsOfOtherAuditors(final String username);
}
