
package acme.entities.courses;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CourseLecture {

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Course	course;

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Lecture	lecture;

}
