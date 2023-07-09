
package acme.features.student.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface StudentDashboardRepository extends AbstractRepository {

	//	@Query("select a from Activity a where a.enrolment.student.id = :id and a.activity_type = :type")
	//	Collection<Activity> activityByType(int id, Nature type);

	//	@Query("select avg(e.workTime) from Enrolment e where e.student.id = :id")
	//	Double averageWorkTimeStudent(int id);
	//
	//	@Query("SELECT STDDEV(e.workTime) from Enrolment e where e.student.id = :id")
	//	Double deviationWorkTimeStudent(int id);
	//
	//	@Query("select min(e.workTime) from Enrolment e where e.student.id = :id")
	//	Double minWorkTimeStudent(int id);
	//
	//	@Query("select max(e.workTime) from Enrolment e where e.student.id = :id")
	//	Double maxWorkTimeStudent(int id);

	@Query("SELECT e FROM Enrolment e WHERE e.student.id = :id")
	Collection<Enrolment> findEnrolmentsByStudentId(int id);

	@Query("SELECT a FROM Activity a WHERE a.enrolment.student.id = :id")
	Collection<Activity> findActivitiesByStudentId(int id);

}
