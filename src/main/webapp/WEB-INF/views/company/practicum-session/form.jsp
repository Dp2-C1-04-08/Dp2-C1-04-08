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
	<acme:input-textbox code="company.practicumSession.list.label.title" path="title"/>
	<acme:input-textbox code="company.practicumSession.list.label.abstractStr" path="abstractStr"/>
	<acme:input-textarea code="company.practicumSession.list.label.link" path="link"/>
	<acme:input-moment code="company.practicumSession.list.label.startDate" path="startDate"/>
	<acme:input-moment code="company.practicumSession.list.label.endDate" path="endDate"/>
	
	

	<jstl:choose>
		<jstl:when test="${_command == 'show' || _command == 'update'|| _command == 'delete'}">
			<acme:submit code="company.practicumSession.list.button.update" action="/company/practicum-session/update"/>
			<acme:submit code="company.practicumSession.list.button.delete" action="/company/practicum-session/delete"/>
			
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="company.practicumSession.list.button.create" action="/company/practicum-session/create?masterId=${masterId}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>