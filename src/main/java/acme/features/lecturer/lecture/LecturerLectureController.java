
package acme.features.lecturer.lecture;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.courses.Lecture;
import acme.framework.controllers.AbstractController;
import acme.roles.Lecturer;

@Controller
public class LecturerLectureController extends AbstractController<Lecturer, Lecture> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureListService	listService;

	@Autowired
	protected LecturerLectureShowService	showService;
	@Autowired
	protected LecturerLectureCreateService	createService;
	@Autowired
	protected LecturerLectureDeleteService	deleteService;
	@Autowired
	protected LecturerLectureUpdateService	updateService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
	}

}
