<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="student.enrolment.form.label.code" path="code"/>	
	<acme:input-textbox code="student.enrolment.form.label.motivation" path="motivation"/>
	<acme:input-textbox code="student.enrolment.form.label.goals" path="goals"/>
	<acme:input-textbox readonly="${true}" code="student.enrolment.form.label.student.name" path="student.name"/>
	<acme:input-textbox readonly="${true}" code="student.enrolment.form.label.course.code" path="course.code"/>
	<acme:input-checkbox code="student.enrolment.form.label.isFinalised" path="isFinalised"/>

	<jstl:choose>
		<jstl:when test="${_command == 'show' || _command == 'update'|| _command == 'delete'}">
			<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>
			<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
			<acme:submit code="student.enrolment.form.button.register" action="/student/enrolment/create"/>
		</jstl:when>
		<jstl:when test="${_command == 'finalise'}">
			<acme:button code="student.enrolment.form.button.finalise" action="/student/enrolment-session/list?masterId=${id}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
