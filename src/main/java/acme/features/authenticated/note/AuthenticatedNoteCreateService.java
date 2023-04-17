
package acme.features.authenticated.note;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.notes.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.accounts.UserAccount;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteCreateService extends AbstractService<Authenticated, Note> {

	@Autowired
	protected AuthenticatedNoteRepository repository;

	// AbstractService interface ----------------------------------------------


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
		Note object;
		object = new Note();
		
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Note object) {

		final String author;
		final Principal principal = super.getRequest().getPrincipal();
		final String username = principal.getUsername();
		final Integer principalId = super.getRequest().getPrincipal().getAccountId();
		UserAccount userAccount = userAccount = this.repository.findOneUserAccountById(principalId);
		final String name = userAccount.getIdentity().getName();
		final String surname = userAccount.getIdentity().getSurname();
		final String email = userAccount.getIdentity().getEmail();
		author = username + "-" + surname + "," + name;
		object.setAuthor(author);
		object.setEmail(email);

		super.bind(object, "title", "message", "link");

	}

	@Override
	public void validate(final Note object) {
		assert object != null;

		final boolean isAccepted = this.getRequest().getData("confirmation", boolean.class);

		super.state(isAccepted, "confirmation", "authentication.note.form.error.notAccepted");

	}

	@Override
	public void perform(final Note object) {
		assert object != null;
		final Date creationDate = MomentHelper.getCurrentMoment();
		object.setCreationDate(creationDate);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Note object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "creationDate", "title", "message", "email", "author", "link");
		super.getResponse().setData(tuple);
	}

}
