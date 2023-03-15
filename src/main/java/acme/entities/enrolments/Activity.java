
package acme.entities.enrolments;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.courses.Nature;
import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Activity extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			title;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			activityAbstract;

	@NotNull
	protected Nature			activityType;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				startTime;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				endTime;

	@URL
	protected String			link;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Enrolment			enrolment;
}
