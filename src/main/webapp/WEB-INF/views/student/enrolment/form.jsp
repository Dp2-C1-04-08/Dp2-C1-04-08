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
	<acme:input-textbox code="student.enrolment.list.label.code" path="code"/>
	<acme:input-textbox code="student.enrolment.list.label.motivation" path="motivation"/>
	<acme:input-textarea code="student.enrolment.list.label.goals" path="goals"/>
	<acme:input-textarea code="student.enrolment.list.label.student" path="student"/>
	<acme:input-integer code="student.enrolment.list.label.course" path="course"/>
	<jstl:choose>
	<jstl:when test="${published==false}">
	<acme:input-checkbox code="student.enrolment.list.label.isFinalised" path="finalised"/>
	</jstl:when>
	<jstl:when test="${published==false}">
	</jstl:when>
	</jstl:choose>

	<jstl:choose>
		<jstl:when test="${_command == 'show' || _command == 'update'|| _command == 'delete' || _command == list}">
			<acme:submit code="student.enrolment.list.button.update" action="/student/enrolment/update"/>
			<acme:submit code="student.enrolment.list.button.delete" action="/student/enrolment/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'finalise'}">
			<acme:button code="student.enrolment.list.button.finalise" action="/student/enrolment-session/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'register'}">
			<acme:submit code="student.enrolment.list.button.register" action="/student/enrolment/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>