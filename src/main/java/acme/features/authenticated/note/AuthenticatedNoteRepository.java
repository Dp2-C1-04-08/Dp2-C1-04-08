
package acme.features.authenticated.note;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.notes.Note;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedNoteRepository extends AbstractRepository {

	@Query("SELECT n FROM Note n WHERE n.creationDate >= :oneMonthAgo")
	List<Note> findAllNoteLastMonth(Date oneMonthAgo);

	@Query("SELECT n FROM Note n WHERE n.id = :id")
	Note findNoteById(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

}
