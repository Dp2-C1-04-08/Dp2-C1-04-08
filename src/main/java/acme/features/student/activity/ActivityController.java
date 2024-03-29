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

package acme.features.student.activity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.enrolments.Activity;
import acme.framework.controllers.AbstractController;
import acme.roles.Student;

@Controller
public class ActivityController extends AbstractController<Student, Activity> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ActivityListService	listService;

	@Autowired
	protected ActivityShowService	showService;

	@Autowired
	protected ActivityCreateService	createService;

	@Autowired
	protected ActivityUpdateService	updateService;

	@Autowired
	protected ActivityDeleteService	deleteService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("create", this.createService);
	}

}
