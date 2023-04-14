/*
 * AuthenticatedAnnouncementRepository.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.student.enrolment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.enrolments.Activity;
import acme.entities.enrolments.Enrolment;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EnrolmentRepository extends AbstractRepository {

	@Query("SELECT e FROM Enrolment e WHERE e.student.id = :id")
	Collection<Enrolment> findEnrolmentsByStudentId(int id);

	@Query("SELECT e FROM Enrolment e WHERE e.id = :id")
	Enrolment findEnrolmentById(int id);

	@Query("SELECT a FROM Activity a WHERE a.enrolment.id = :id")
	Collection<Activity> findActivitiesByStudent(int id);

}
