
package acme.features.authenticated.peep;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.peeps.Peep;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedPeepRepository extends AbstractRepository {

	@Query("select p from Peep a where p.id = :id")
	Peep findOneAnnouncementById(int id);

	@Query("select p from Peep a where p.instanciationMoment >= :deadline")
	Collection<Peep> findRecentAnnouncements(Date deadline);

}
