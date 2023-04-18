
package acme.forms;

import java.util.List;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	List<Integer>				handsOnPracticum;
	List<Integer>				theoreticalPracticum;
	Double						averageSession;
	Double						deviationSession;
	Double						minimumSession;
	Double						maximumSession;
	Double						averagePractica;
	Double						deviationPractica;
	Integer						minimumPractica;
	Integer						maximumPractica;

}
