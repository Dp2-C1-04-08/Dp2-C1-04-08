
package acme.entities.notes;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;

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
	protected Date				CreationDate;

	@NotBlank
	@NotNull
	@Length(max = 75)
	protected String			title;

	@NotNull
	@NotBlank
	@Length(max = 100)
	protected String			message;

	@NotNull
	@NotBlank
	@Email
	protected String			email;

	/*
	 * @ManyToOne(optional = false)
	 * protected UserIdentity userAuthor;
	 */
	@NotNull
	@Valid
	@Length(max = 75)
	protected String			author;

	/*
	 * @PrePersist
	 * public void setAuthor() {
	 * String parsedAuthor = " - "+userAuthor.getFullName();
	 * this.author = parsedAuthor;
	 * }
	 */
}
