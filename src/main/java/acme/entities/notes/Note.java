
package acme.entities.notes;

import java.util.Date;

import javax.persistence.Entity;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;

import javax.persistence.TemporalType;
import acme.datatypes.UserIdentity;

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

	@Length(max = 101)

	protected String			message;

	@NotNull
	@NotBlank
	@Email
	protected String			email;

	@NotNull
	@Valid
	@Length(max = 75)

	@ManyToOne(optional = false)
	protected UserIdentity		author;


	public String getAuthor() {
		return this.author.getSurname() + "," + this.author.getName();
	}

	protected String			author;

	@URL
	protected String			link;


}
