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
	<acme:input-textbox code="any.practica.list.label.code" path="code"/>
	<acme:input-textbox code="any.practica.list.label.title" path="title"/>
	<acme:input-textarea code="any.practica.list.label.goals" path="goals"/>
	<acme:input-textarea code="any.practica.list.label.abstractStr" path="abstractStr"/>
	<acme:input-select code="any.practica.list.label.course" path="course" choices="${courses}"/>	
	<acme:input-checkbox code="any.practica.list.label.published" path="published"/>
	
	<acme:button code="any.practica.button.show.company" action="/any/company/show?masterId=${company.id}"/>
		
</acme:form>