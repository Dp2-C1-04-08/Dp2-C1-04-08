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
	<acme:input-textbox code="assistant.sessionTutorial.form.label.title" path="title"/>
	<acme:input-textbox code="assistant.sessionTutorial.form.label.abstract-str" path="abstractStr"/>
	<acme:input-select code="assistant.sessionTutorial.form.label.type" path="type" choices="${types}"/>
	<acme:input-url code="assistant.sessionTutorial.form.label.link" path="link"/>
	<acme:input-moment code="assistant.sessionTutorial.form.label.startTime" path="startTime"/>
	<acme:input-moment code="assistant.sessionTutorial.form.label.endTime" path="endTime"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.sessionTutorial.form.button.create" action="/assistant/session-tutorial/create?masterId=${masterId}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode}">
			<acme:submit code="assistant.Sessiontutorial.form.button.update" action="/assistant/session-tutorial/update"/>
			<acme:submit code="assistant.Sessiontutorial.form.button.delete" action="/assistant/session-tutorial/delete"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
