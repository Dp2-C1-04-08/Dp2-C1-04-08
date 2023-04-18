
package acme.features.authenticated.note;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.notes.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteListService extends AbstractService<Authenticated, Note> {

	@Autowired
	protected AuthenticatedNoteRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Note> objects;
		final Date actualDate = MomentHelper.getCurrentMoment();
		final Instant instant = actualDate.toInstant();
		final Duration duracion = Duration.ofDays(30);
		final Instant instantAnterior = instant.minus(duracion);

		final Date oneMonthAgo = Date.from(instantAnterior);
		objects = this.repository.findAllNoteLastMonth(oneMonthAgo);

		super.getBuffer().setData(objects);
	}

	@Override
	public void unbind(final Note object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "creationDate", "title", "message", "email", "author", "link");

		super.getResponse().setData(tuple);
	}

}
