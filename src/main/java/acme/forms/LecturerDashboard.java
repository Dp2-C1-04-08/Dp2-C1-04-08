
package acme.forms;

import java.util.Map;

import acme.entities.courses.Nature;
import acme.framework.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LecturerDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------
	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Map<Nature, Integer>		totalLecture;
	Double						averageLecture;
	Double						deviationLecture;
	Double						minimumLecture;
	Double						maximumLecture;
	Double						averageCourse;
	Double						deviationCourse;
	Double						minimumCourse;
	Double						maximumCourse;

}
