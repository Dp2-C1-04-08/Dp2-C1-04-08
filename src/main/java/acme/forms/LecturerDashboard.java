
package acme.forms;

import java.util.Map;

import acme.entities.courses.Nature;

public class LecturerDashboard {

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
