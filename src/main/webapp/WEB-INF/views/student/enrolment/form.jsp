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
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' || _command == 'update'|| _command == 'delete' || _command == 'register'}">
			<acme:input-textbox code="student.enrolment.form.label.code" path="code"/>	
			<acme:input-textbox code="student.enrolment.form.label.motivation" path="motivation"/>
			<acme:input-textbox code="student.enrolment.form.label.goals" path="goals"/>
			
			<jstl:choose>
				<jstl:when test="${_command == 'show'}">
					<acme:input-textbox readonly="${true}" code="student.enrolment.form.label.course-code" path="code"/>
				</jstl:when>
				<jstl:when test="${_command == 'show' || _command == 'update'|| _command == 'delete'}">
					<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>
					<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
					<acme:button code="student.enrolment.form.button.finalise" action="/student/enrolment/finalise"/>
					<acme:button code="student.enrolment.form.button.activities" action="/student/activity/list?id=${id}"/>
					
				</jstl:when>
				<jstl:when test="${_command == 'register'}">
					<acme:submit code="student.enrolment.form.button.register" action="/student/enrolment/register"/>
				</jstl:when>
			</jstl:choose>
		</jstl:when>
		
		<jstl:when test="${_command == 'finalise'}">
			<acme:input-textbox code="student.enrolment.form.label.code" path="code"/>	
			<acme:input-textbox code="student.enrolment.form.label.creditCardHolder" path="creditCardHolder"/>
			<acme:input-textbox code="student.enrolment.form.label.expiryDate" path="expiryDate"/>
			<acme:input-textbox code="student.enrolment.form.label.cvc" path="cvc"/>
			<acme:input-textbox code="student.enrolment.form.label.upperNibble" path="upperNibble"/>
			<acme:input-textbox code="student.enrolment.form.label.lowerNibble" path="lowerNibble"/>

			<acme:button code="student.enrolment.form.button.finalise" action="/student/enrolment-session/finalise"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
