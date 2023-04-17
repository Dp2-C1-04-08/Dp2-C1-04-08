
package acme.features.authenticated.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedCourseListService extends AbstractService<Authenticated, Course> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedCourseRepository repository;

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
		Collection<Course> object;

		object = this.repository.findManyCoursesByAvailability();

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
