
package acme.features.auditor.audit;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.audits.Audit;
import acme.entities.courses.Course;
import acme.framework.components.accounts.Principal;
import acme.framework.components.jsp.SelectChoices;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Auditor;

@Service
public class AuditorAuditPublishService extends AbstractService<Auditor, Audit> {

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
		Audit object;
		Integer exists;
		int id;
		Boolean status;

		principal = super.getRequest().getPrincipal();
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditById(id);
		exists = this.repository.existsAuditWithIdForAuditor(id, principal.getActiveRoleId());
		status = !object.getPublished() && exists.equals(1);

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
	public void bind(final Audit object) {
		assert object != null;
		int courseId;
		Course course;
		super.bind(object, "code", "conclusion", "strongPoints", "weakPoints");

		courseId = super.getRequest().getData("course", int.class);
		course = this.repository.findOneCourseById(courseId);
		object.setCourse(course);
	}

	@Override
	public void validate(final Audit object) {
		String code;

		code = super.getRequest().getData("code", String.class);
		Optional<Audit> auditWithSameCode;
		auditWithSameCode = this.repository.findAuditByCode(code);
		super.state(!auditWithSameCode.isPresent() || object.getId() == auditWithSameCode.get().getId(), "code", "auditor.audit.form.error.code.duplicated");
		super.state(!object.getPublished(), "*", "auditor.audit.form.error.general.update.published");
	}

	@Override
	public void perform(final Audit object) {
		assert object != null;
		object.setPublished(true);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Audit object) {
		assert object != null;

		Tuple tuple;
		Collection<Course> courses;
		final SelectChoices choices;
		courses = this.repository.findValidCourses();
		choices = SelectChoices.from(courses, "title", object.getCourse());

		tuple = super.unbind(object, "code", "conclusion", "strongPoints", "weakPoints", "published");
		tuple.put("courses", choices);
		tuple.put("course", choices.getSelected().getKey());
		super.getResponse().setData(tuple);
	}

}
