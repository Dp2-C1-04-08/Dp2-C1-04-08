
package acme.entities.practicums;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import acme.entities.courses.Course;
import acme.framework.data.AbstractEntity;
import acme.roles.Company;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Practicum extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "[A-Z]{1,3}[0-9][0-9]{3}")
	protected String			code;

	@NotBlank
	@Length(min = 1, max = 75)
	protected String			title;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			goals;

	@NotBlank
	@Length(min = 1, max = 100)
	protected String			abstractStr;

	@PositiveOrZero
	@NotNull
	protected Integer			estimatedTime;

	@NotNull
	protected Boolean			published;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Company			company;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Course			course;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	protected Date				creationDate;

}
