
package acme.forms;

import java.util.Map;

import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	Map<String, Integer>		numberOfAuditsForCoursesByType;
	Double						averageNumberOfRecordsInAudit;
	Double						deviationNumberOfRecordsInAudit;
	Double						maximumNumberOfRecordsInAudit;
	Double						minimumNumberOfRecordsInAudit;
	Double						averageLengthOfPeriodInRecords;
	Double						deviationLengthOfPeriodInRecords;
	Double						maximumLengthOfPeriodInRecords;
	Double						minimumLengthOfPeriodInRecords;

}
