
package acme.entities.tutorials;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import acme.roles.Assistant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Tutorial extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			title;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			abstactStr;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			goals;

	@NotNull
	@PositiveOrZero
	protected Double			estimatedTotalTime;

	protected boolean			draft;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Assistant			assistant;

	//	@NotNull
	//	@Valid
	//	@ManyToOne(optional = false)
	//	protected Course			course;

}
