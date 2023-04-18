
/*
 * AuthenticatedAnnouncementShowService.java
 *
 * Copyright (C) 2012-2023 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.student.enrolment;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolments.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class EnrolmentFinaliseService extends AbstractService<Student, Enrolment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected EnrolmentRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Enrolment enrolment;

		id = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(id);
		status = !enrolment.getIsFinalised();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Enrolment enrolment = this.repository.findEnrolmentById(id);
		boolean isFinalised;
		isFinalised = enrolment.getCreditCardHolder() != null && enrolment.getLowerNibble() != null;
		enrolment.setIsFinalised(isFinalised);
		super.getBuffer().setData(enrolment);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		super.bind(object, "code", "motivation", "goals", "student", "course", "creditCardHolder", "lowerNibble", "isFinalised");

	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		final Boolean isCCCAccepted = this.getRequest().getData("creditCardHolder", String.class) != null;
		final Boolean isExpiryDateAccepted = this.getRequest().getData("expiryDate", Date.class) != null;
		final Boolean isCCVAccepted = this.getRequest().getData("cvc", int.class) != null;
		final boolean isUpperNibbleAccepted = this.getRequest().getData("upperNibble", int.class) != null;
		final boolean isLowerNibbleAccepted = this.getRequest().getData("lowerNibble", int.class) != null;

		super.state(isCCCAccepted, "creditCardHolder", "authentication.note.form.error.notAccepted");
		super.state(isExpiryDateAccepted, "expiryDate", "authentication.note.form.error.notAccepted");
		super.state(isCCVAccepted, "cvc", "authentication.note.form.error.notAccepted");
		super.state(isLowerNibbleAccepted, "lowerNibble", "authentication.note.form.error.notAccepted");
		super.state(isUpperNibbleAccepted, "upperNibble", "authentication.note.form.error.notAccepted");

		final boolean isFinalised = object.getIsFinalised();
		super.state(isFinalised, "title", "enrolment.form.error.finalise.finalised");

	}

	@Override
	public void perform(final Enrolment object) {
		assert object != null;
		object.setIsFinalised(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Enrolment object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "motivation", "goals", "student", "course", "creditCardHolder", "lowerNibble", "isFinalised");

		super.getResponse().setData(tuple);
	}

}
