
package acme.features.any.company;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.framework.components.accounts.Any;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;

@Controller
public class AnyCompanyController extends AbstractController<Any, Company> {

	//Internal state ----------------------------------

	@Autowired
	protected AnyCompanyShowService showService;

	//Contructors ---------------------------------------------


	@PostConstruct
	protected void initialise() {

		super.addBasicCommand("show", this.showService);

	}

}
