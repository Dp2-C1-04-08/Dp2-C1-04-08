
package acme.forms;

import java.util.Map;

import acme.entities.courses.Nature;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistantDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------
	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<Nature, Integer>		tutorialsRatio;
	Double						tutorialsAverage;
	Double						tutorialsDeviation;
	Double						tutorialsMinimum;
	Double						tutorialsMaximum;
	Double						sessionsAverage;
	Double						sessionsDeviation;
	Double						sessionsMinimum;
	Double						sessionsMaximum;
}
