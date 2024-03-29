
package acme.entities.notes;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Note extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@PastOrPresent
	@NotNull
	protected Date				creationDate;

	@NotBlank
	@NotNull
	@Length(max = 75)
	protected String			title;

	@NotNull
	@NotBlank

	@Length(max = 100)
	protected String			message;

	@Email
	protected String			email;

	@NotNull
	@Length(max = 75)
	protected String			author;

	@URL
	protected String			link;

}
