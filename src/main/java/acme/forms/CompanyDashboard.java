
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	Map<String, Integer>		totalSession;
	Double						averageSession;
	Double						deviationSession;
	Double						minimumSession;
	Double						maximumSession;
	Double						averagePractica;
	Double						deviationPractica;
	Double						minimumPractica;
	Double						maximumPractica;

}
