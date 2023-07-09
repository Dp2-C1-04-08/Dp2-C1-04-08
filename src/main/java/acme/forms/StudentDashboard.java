
package acme.forms;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------
	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						numberoOfTheoryActivities;
	Integer						numberOfHandsOnActivities;
	Double						averagePeriodOfActivities;
	Double						deviationPeriodOfActivities;
	Double						minimumPeriodOfActivities;
	Double						maximumPeriodOfActivities;
	Double						averageLearningTime;
	Double						deviationLearningTime;
	Double						minimumLearningTime;
	Double						maximumLearningTime;
}
