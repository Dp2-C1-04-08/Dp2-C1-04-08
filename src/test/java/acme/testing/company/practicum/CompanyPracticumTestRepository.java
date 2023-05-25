
package acme.testing.company.practicum;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicums.Practicum;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface CompanyPracticumTestRepository extends AbstractRepository {

	@Query("SELECT p from Practicum p WHERE p.company.name=:name")
	Collection<Practicum> findPracticumByCompanyName(String name);

}
