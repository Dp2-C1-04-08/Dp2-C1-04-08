
package acme.components;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.systemConfiguration.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface SystemCurrencyRepository extends AbstractRepository {

	@Query("select sc from SystemConfiguration sc")
	List<SystemConfiguration> getSystemConfiguration();
}
