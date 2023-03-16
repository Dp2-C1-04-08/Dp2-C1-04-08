
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------
	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	Map<String, Integer>		numberOfPrincipalWithEachRole;
	Double						ratioOfPeepsWithEmailAndLink;
	Double						ratioOfCriticalBulletins;
	Double						ratioOfNonCriticalBulletins;
	Map<String, Double>			averageBudgetInOffersGroupedByCurrency;
	Map<String, Double>			minimumBudgetInOffersGroupedByCurrency;
	Map<String, Double>			maximumBudgetInOffersGroupedByCurrency;
	Map<String, Double>			standardDeviationBudgetInOffersGroupedByCurrency;
	Double						averageNumberOfNotesPostedLastTenWeeks;
	Double						minimumNumberOfNotesPostedLastTenWeeks;
	Double						maximumNumberOfNotesPostedLastTenWeeks;
	Double						standardDeviationNumberOfNotesPostedLastTenWeeks;
}
