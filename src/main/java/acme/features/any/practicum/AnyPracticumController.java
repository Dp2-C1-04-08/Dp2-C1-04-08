
package acme.features.any.practicum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.practicums.Practicum;
import acme.framework.components.accounts.Any;
import acme.framework.controllers.AbstractController;

@Controller
public class AnyPracticumController extends AbstractController<Any, Practicum> {

	//Internal state ----------------------------------
	@Autowired
	protected AnyPracticumListService	listService;

	@Autowired
	protected AnyPracticumShowService	showService;

	//Contructors ---------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);

	}
}
