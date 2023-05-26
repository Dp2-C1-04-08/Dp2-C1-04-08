
package acme.testing.student.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.enrolments.Enrolment;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentEnrolmentTestRepository extends AbstractRepository {

	@Query("SELECT e FROM Enrolment e WHERE e.student.id = :id")
	Collection<Enrolment> findEnrolmentsByStudentId(int id);

}
