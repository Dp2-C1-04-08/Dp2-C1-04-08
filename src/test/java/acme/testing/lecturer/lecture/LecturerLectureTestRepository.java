
package acme.testing.lecturer.lecture;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.courses.Lecture;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface LecturerLectureTestRepository extends AbstractRepository {

	@Query("SELECT l FROM Lecture l WHERE l.lecturer.resume= :resume")
	List<Lecture> findLectureByLecturerResume(String resume);

}
