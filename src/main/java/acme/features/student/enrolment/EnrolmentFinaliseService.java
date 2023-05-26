
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

import java.time.Duration;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolments.Enrolment;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
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
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		boolean status;
		int id;
		Enrolment enrolment;

		id = super.getRequest().getData("id", int.class);
		enrolment = this.repository.findEnrolmentById(id);
		status = !enrolment.getIsFinalised() && super.getRequest().getPrincipal().hasRole(enrolment.getStudent()) && super.getRequest().getPrincipal().getActiveRoleId() == enrolment.getStudent().getId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Enrolment enrolment = this.repository.findEnrolmentById(id);
		super.getBuffer().setData(enrolment);
	}

	@Override
	public void bind(final Enrolment object) {
		assert object != null;

		super.bind(object, "creditCardHolder", "lowerNibble");

	}

	@Override
	public void validate(final Enrolment object) {
		assert object != null;

		final Date expiryDate = this.getRequest().getData("expiryDate", Date.class);
		final String cvc = this.getRequest().getData("cvc", String.class);
		final String upperNibble = this.getRequest().getData("upperNibble", String.class);
		final String lowerNibble = this.getRequest().getData("lowerNibble", String.class);

		final Boolean isExpiryDateAccepted = expiryDate != null;
		if (isExpiryDateAccepted) {
			final Duration untilExpiry = MomentHelper.computeDuration(expiryDate, MomentHelper.getCurrentMoment());
			final boolean isExpiryDateValid = isExpiryDateAccepted && untilExpiry.isNegative();
			super.state(isExpiryDateValid, "expiryDate", "authentication.note.form.error.expired");
		}

		super.state(cvc.length() == 3, "cvc", "authentication.note.form.error.wrongLength");
		super.state(upperNibble.length() == 8, "upperNibble", "authentication.note.form.error.wrongLength");
		super.state(lowerNibble.length() == 8, "lowerNibble", "authentication.note.form.error.wrongLength");
		super.state(isExpiryDateAccepted, "expiryDate", "authentication.note.form.error.null");

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
