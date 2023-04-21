
package acme.features.administrator.systemConfiguration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.systemConfiguration.SystemConfiguration;
import acme.framework.components.accounts.Administrator;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AdministratorSystemConfigurationUpdateService extends AbstractService<Administrator, SystemConfiguration> {

	@Autowired
	protected AdministratorSystemConfigurationRepository repository;


	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SystemConfiguration systemConfiguration;
		int id;

		id = super.getRequest().getData("id", int.class);
		systemConfiguration = this.repository.findSystemConfigurationById(id);
		super.getBuffer().setData(systemConfiguration);
	}
	@Override
	public void bind(final SystemConfiguration object) {
		assert object != null;
		super.bind(object, "systemCurrency", "acceptedCurrencies");

	}
	@Override
	public void validate(final SystemConfiguration object) {
		assert object != null;
		final String acceptedCurrenciesStr = object.getAcceptedCurrencies();
		final String systemCurrency = object.getSystemCurrency();

		final List<String> currencies = new ArrayList<>();

		for (String currencyStr : acceptedCurrenciesStr.split(";")) {
			currencyStr = currencyStr.trim();
			currencies.add(currencyStr);
		}

		final boolean validAcceptedCurrencies = !acceptedCurrenciesStr.contains("\"") && !acceptedCurrenciesStr.contains("-") && !acceptedCurrenciesStr.contains(",") && !acceptedCurrenciesStr.contains("_");
		super.state(currencies.contains(systemCurrency), "systemCurrency", "administrator.system-configuration.form.error.notInAcceptedCurrencies");
		super.state(validAcceptedCurrencies, "acceptedCurrencies", "administrator.system-configuration.form.error.invalidFormat");
	}

	@Override
	public void perform(final SystemConfiguration object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final SystemConfiguration object) {

		assert object != null;
		Tuple tuple;

		tuple = super.unbind(object, "systemCurrency", "acceptedCurrencies");
		tuple.put("id", object.getId());

		super.getResponse().setData(tuple);
	}

}
