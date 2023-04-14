/*
 * AuthenticatedAnnouncementController.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.student.enrolment;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.enrolments.Enrolment;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class EnrolmentController extends AbstractController<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected EnrolmentListService		listService;

	@Autowired
	protected EnrolmentShowService		showService;

	@Autowired
	protected EnrolmentRegisterService	registerService;

	@Autowired
	protected EnrolmentUpdateService	updateService;

	@Autowired
	protected EnrolmentDeleteService	deleteService;

	@Autowired
	protected EnrolmentFinaliseService	finaliseService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		//	super.addBasicCommand("register", this.registerService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		//		super.addBasicCommand("finalise", this.finaliseService) poner los atributos como parte del enrolment y controlar que o los dos sean nulos o ninguno en el servicio;
	}

}
