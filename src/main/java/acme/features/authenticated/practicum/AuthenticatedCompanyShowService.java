
package acme.features.authenticated.practicum;

import org.springframework.stereotype.Service;

import acme.framework.components.accounts.Authenticated;
import acme.framework.services.AbstractService;
import acme.roles.Company;

@Service
public class AuthenticatedCompanyShowService extends AbstractService<Authenticated, Company> {

}
