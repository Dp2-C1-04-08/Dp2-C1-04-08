
package acme.features.any.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.courses.Course;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyCourseShowService extends AbstractService<Any, Course> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyCourseRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
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
