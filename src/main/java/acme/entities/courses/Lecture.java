
package acme.entities.courses;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import acme.roles.Lecturer;

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
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startTime;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endTime;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			body;

	protected Nature			lectureType;

	@URL
	protected String			link;

	@NotNull
	@Valid
	protected Lecturer			lecturer;

}
