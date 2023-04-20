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

<jstl:set var="f" value="${isFinalised}"/>

<acme:form>
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' || _command == 'update'|| _command == 'delete' || _command == 'register'}">
			<acme:input-textbox code="student.enrolment.form.label.code" path="code"/>	
			<acme:input-textbox code="student.enrolment.form.label.motivation" path="motivation"/>
			<acme:input-textbox code="student.enrolment.form.label.goals" path="goals"/>
			
			<jstl:choose>
				<jstl:when test="${_command == 'show' || _command == 'update'|| _command == 'delete'}">
					<acme:input-textbox readonly="${true}" code="student.enrolment.form.label.course-code" path="code"/>
					<jstl:choose>
						<jstl:when test="${f == false}">
							<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>
							<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
							<acme:button code="student.enrolment.form.button.finalise" action="/student/enrolment/finalise?id=${id}"/>							
						</jstl:when>
					</jstl:choose>
					<acme:button code="student.enrolment.form.button.activities" action="/student/activity/list?masterId=${id}"/>
					
				</jstl:when>
				<jstl:when test="${_command == 'register'}">
					<acme:submit code="student.enrolment.form.button.register" action="/student/enrolment/register"/>
				</jstl:when>
			</jstl:choose>
		</jstl:when>
		
		<jstl:when test="${_command == 'finalise'}">
			<acme:input-textbox readonly = "${true}" code="student.enrolment.form.label.code" path="code"/>	
			<acme:input-textbox code="student.enrolment.form.label.creditCardHolder" path="creditCardHolder"/>
			<acme:input-moment code="student.enrolment.form.label.expiryDate" path="expiryDate"/>
			<acme:input-integer code="student.enrolment.form.label.cvc" path="cvc"/>
			<acme:input-integer code="student.enrolment.form.label.upperNibble" path="upperNibble"/>
			<acme:input-integer code="student.enrolment.form.label.lowerNibble" path="lowerNibble"/>

			<acme:submit code="student.enrolment.form.button.finalise" action="/student/enrolment/finalise?id=${id}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
