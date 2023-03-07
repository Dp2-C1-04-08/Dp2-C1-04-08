
package acme.entities.courses;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;

public class Lecture extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			title;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			abstractStr;

	@NotNull
	@Positive
	protected Double			estimatedLearningTime;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			body;

	protected Nature			lectureType;

	@URL
	protected String			link;

	@ManyToOne
	protected Course			course;

}
