
package acme.testing.student.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.enrolments.Activity;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentActivityTestRepository extends AbstractRepository {

	@Query("SELECT a FROM Activity a WHERE a.enrolment.student.id = :id")
	Collection<Activity> findActivitiesByEnrolmentId(int id);

}
