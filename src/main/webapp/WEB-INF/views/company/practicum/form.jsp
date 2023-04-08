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
	<acme:input-textbox code="company.practica.list.label.code" path="code"/>
	<acme:input-textbox code="company.practica.list.label.title" path="title"/>
	<acme:input-textarea code="company.practica.list.label.goals" path="goals"/>
	<acme:input-textarea code="company.practica.list.label.abstractStr" path="abstractStr"/>
	<acme:input-select code="company.practica.list.label.course" path="course" choices="${courses}"/>	
	
	<acme:input-checkbox code="company.practica.list.label.published" path="published"/>
	

	<jstl:choose>
		<jstl:when test="${_command == 'show' || _command == 'update'|| _command == 'delete' || _command == list}">
			<acme:submit code="company.practica.list.button.update" action="/company/practicum/update"/>
			<acme:submit code="company.practica.list.button.delete" action="/company/practicum/delete"/>
			<acme:button code="company.practicumSession.list.button" action="/company/practicum-session/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="company.practica.list.button.create" action="/company/practicum/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>