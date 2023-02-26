
package acme.entities.practicumSessions;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import acme.framework.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PracticumSession extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Length(min = 0, max = 75)
	protected String			title;

	@NotBlank
	@Length(min = 0, max = 100)
	protected String			abstracto; //el atributo es abstract pero lo tiene como palabra reservada

	//falta el atibuto timePeriod 

}
