
package acme.features.authenticated.peep;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.framework.components.accounts.Authenticated;
import acme.framework.controllers.AbstractController;
import acme.roles.Company;

@Controller
public class AuthenticatedPeepController extends AbstractController<Authenticated, Company> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedPeepListService listService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.listService);

	}
}
