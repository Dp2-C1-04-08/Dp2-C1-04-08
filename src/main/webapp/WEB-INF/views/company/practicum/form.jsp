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

<jstl:set var="minus10EstimatedTime" value="${estimatedTime - 0.1*estimatedTime }"/>
<jstl:set var="plus10EstimatedTime" value="${estimatedTime + 0.1*estimatedTime }"/>
<jstl:set var="isPublished" value="${published }"/>


<acme:form readonly="${isPublished == true }">
	<acme:input-textbox code="company.practica.list.label.code" path="code" placeholder="[A-Z]{1,3}[0-9][0-9]{3}"/>
	<acme:input-textbox code="company.practica.list.label.title" path="title"/>
	<acme:input-textarea code="company.practica.list.label.goals" path="goals"/>
	<acme:input-textarea code="company.practica.list.label.abstractStr" path="abstractStr"/>
	<acme:input-select code="company.practica.list.label.course" path="course" choices="${courses}"/>
	<jstl:if test="${_command == 'show' || _command == 'update'|| _command == 'delete' || _command == 'list' || _command == 'publish'}">
	<acme:input-textbox readonly = "${true }" code="company.practica.list.label.minus10estimatedTime" path="minus10estimatedTime"/>
	<acme:input-textbox readonly = "${true }" code="company.practica.list.label.plus10estimatedTime" path="plus10estimatedTime"/>
			</jstl:if>
	
	
	<acme:input-checkbox code="company.practica.list.label.published" path="published" readonly="true"  />
	

	<jstl:choose>
		<jstl:when test="${_command == 'show' || _command == 'update'|| _command == 'delete' || _command == 'list' || _command == 'publish'}">
			
			<acme:submit code="company.practica.list.button.update" action="/company/practicum/update"/>
			<acme:submit code="company.practica.list.button.delete" action="/company/practicum/delete"/>
			<jstl:if test="${!isPublished }">
			<acme:submit code="company.practica.form.button.publish" action="/company/practicum/publish"/>
			</jstl:if>
			
			<acme:button code="company.practicumSession.list.button" action="/company/practicum-session/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="company.practica.list.button.create" action="/company/practicum/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>