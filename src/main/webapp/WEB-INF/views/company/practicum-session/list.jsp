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

<acme:list>

	<acme:list-column code="company.practicumSession.list.label.title" path="title"/>
	<acme:list-column code="company.practicumSession.list.label.addendum" path="addendum"/>
	<acme:list-column code="company.practicumSession.list.label.link" path="link"/>
	<acme:list-column code="company.practicumSession.list.label.duration" path="duration"/>
	
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="company.practica.list.button.create" action="/company/practicum-session/create?masterId=${masterId}"/>
</jstl:if>


