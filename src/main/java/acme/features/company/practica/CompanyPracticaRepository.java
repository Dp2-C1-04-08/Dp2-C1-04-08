
package acme.features.company.practica;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Course;
import acme.entities.practicums.Practicum;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Company;

@Repository
public interface CompanyPracticaRepository extends AbstractRepository {

	@Query("SELECT practica FROM Practicum practica")
	List<Practicum> findAllPractica();

	@Query("SELECT practica FROM Practicum practica WHERE practica.course.id = :id")
	List<Practicum> findAllPracticaByCourseId(int id);

	@Query("SELECT p FROM Practicum p WHERE p.id = :id")
	Practicum findPracticaById(int id);

	@Query("SELECT p FROM Practicum p WHERE p.company.id = :id")
	List<Practicum> findPracticaByCompanyId(int id);

	@Query("SELECT c FROM Company c WHERE c.id = :id")
	Company findCompanyById(int id);

	@Query("SELECT c FROM Course c")
	Collection<Course> findAllCourse();

	@Query("SELECT c FROM Course c WHERE c.id=:id")
	Course findCourseById(int id);

	@Query("Select p From Practicum p where p.code = :code")
	Optional<Practicum> findOnePracticumByCode(String code);

}
