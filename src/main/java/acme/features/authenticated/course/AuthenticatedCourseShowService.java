
package acme.features.authenticated.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedCourseShowService extends AbstractService<Authenticated, Course> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedCourseRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}
	@Override
	public void authorise() {

		final int id = super.getRequest().getData("id", int.class);
		final Course object = this.repository.findOneCourseById(id);

		super.getResponse().setAuthorised(!object.isDraft());

	}

	@Override
	public void load() {
		int id;
		Course object;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCourseById(id);

		super.getBuffer().setData(object);
	}

	@Override

	public void unbind(final Course object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "title", "code", "courseAbstract", "courseType", "retailPrice", "link", "lecturer");

		super.getResponse().setData(tuple);
	}
}
