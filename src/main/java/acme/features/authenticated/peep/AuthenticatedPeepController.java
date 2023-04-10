
package acme.features.authenticated.peep;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.peeps.Peep;
import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;

@Controller
public class AuthenticatedPeepController extends AbstractController<Authenticated, Peep> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedPeepListService	listService;

	@Autowired
	protected AuthenticatedPeepShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);

	}
}
