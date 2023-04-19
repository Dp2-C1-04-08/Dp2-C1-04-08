
package acme.entities.systemConfiguration;

import java.util.ArrayList;
import java.util.List;

public class MoneyService {

	private static List<String> currencies = MoneyService.iniciateCurrencies();


	private static List<String> iniciateCurrencies() {
		final List<String> result = new ArrayList<>();
		result.add("EUR");
		result.add("USD");
		result.add("GBP");
		return result;
	}

	public static boolean checkContains(final String currency) {
		return MoneyService.currencies.contains(currency);
	}
}
