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

<jstl:set var="finalised" value="${enrolment-isFinalised}"/>

<acme:form readonly="${finalised == 1}">
	<acme:input-textbox code="student.activity.form.label.title" path="title"/>	
	<acme:input-textbox code="student.activity.form.label.activityAbstract" path="activityAbstract"/>
	<acme:input-select code="student.activity.form.label.activityType" path="activityType" choices="${activityTypes}"/>
	<acme:input-moment code="student.activity.form.label.startTime" path="startTime"/>
	<acme:input-moment code="student.activity.form.label.endTime" path="endTime"/>
	<acme:input-url code="student.activity.form.label.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' || _command == 'update'|| _command == 'delete'}">
			<acme:submit code="student.activity.form.button.update" action="/student/activity/update"/>
			<acme:submit code="student.activity.form.button.delete" action="/student/activity/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}"> 
			<acme:submit code="student.activity.form.button.create" action="/student/activity/create?id=${id}"/>
		</jstl:when>
	</jstl:choose>
		

</acme:form>
