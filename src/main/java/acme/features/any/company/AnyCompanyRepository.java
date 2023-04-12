
package acme.features.any.company;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface AnyCompanyRepository extends AbstractRepository {

	@Query("select c from Company c where c.id = :id")
	Company findCompanyById(int id);

}
