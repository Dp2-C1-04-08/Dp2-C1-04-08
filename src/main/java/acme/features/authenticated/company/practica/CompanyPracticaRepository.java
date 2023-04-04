
package acme.features.authenticated.company.practica;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.practicums.Practicum;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticaRepository extends AbstractRepository {

	@Query("SELECT practica FROM Practicum practica")
	List<Practicum> findAllPractica();

	@Query("SELECT p FROM Practicum p WHERE p.id = :id")
	Practicum findPracticaById(int id);

	@Query("SELECT p FROM Practicum p WHERE p.company.id = :id")
	List<Practicum> findPracticaByCompanyId(int id);

	@Query("SELECT c FROM Company c WHERE c.id = :id")
	Company findCompanyById(int id);

}
