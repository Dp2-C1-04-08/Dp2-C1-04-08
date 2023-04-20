
package acme.features.auditor.audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.audits.MarkValue;
import acme.entities.courses.Course;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditShowService extends AbstractService<Auditor, Audit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuditorAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		Principal principal;
		Integer exists;
		int id;
		Boolean status;

		principal = super.getRequest().getPrincipal();
		id = super.getRequest().getData("id", int.class);
		exists = this.repository.existsAuditWithIdForAuditor(id, principal.getActiveRoleId());
		status = exists.equals(1);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Audit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;
		Collection<Course> courses;
		final SelectChoices choicesMark;
		final SelectChoices choices;
		courses = this.repository.findAllCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());
		choicesMark = SelectChoices.from(MarkValue.class, object.getMark());

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "mark", "published");
		tuple.put("course", object.getCourse().getId());
		tuple.put("courses", choices);
		tuple.put("marks", choicesMark);

		super.getResponse().setData(tuple);
	}

}
