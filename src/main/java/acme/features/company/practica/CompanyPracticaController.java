
package acme.features.company.practica;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.practicums.Practicum;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;

@Controller
public class CompanyPracticaController extends AbstractController<Company, Practicum> {

	//Internal state ----------------------------------
	@Autowired
	protected CompanyPracticaListService	listService;

	@Autowired
	protected CompanyPracticaShowService	showService;

	@Autowired
	protected CompanyPracticaCreateService	createService;

	@Autowired
	protected CompanyPracticaUpdateService	updateService;

	@Autowired
	protected CompanyPracticaDeleteService	deleteService;

	@Autowired
	protected CompanyPracticaPublishService	publishService;

	//Contructors ---------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("publish", "update", this.publishService);
	}
}
