
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<jstl:set var="isDraft" value="${draft }"/>

	
<acme:form readonly="${isDraft == false }">
	<acme:input-textbox code="lecturer.course.form.label.code" path="code" placeholder="[A-Z]{1,3}[0-9][0-9]{3}"/>
	<acme:input-textbox code="lecturer.course.form.label.title" path="title"/>
	<acme:input-textarea code="lecturer.course.form.label.course-abstract" path="courseAbstract"/>
	<acme:input-textbox code="lecturer.course.form.label.course-type" path="courseType"/>
	<acme:input-money code="lecturer.course.form.label.retail-price" path="retailPrice"/>
	<acme:input-url code="lecturer.course.form.label.link" path="link"/>
	<acme:input-checkbox code="lecturer.course.form.label.draft" path="draft"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' || _command == 'update'|| _command == 'delete'}">
			<acme:submit code="lecturer.course.form.button.update" action="/lecturer/course/update"/>
			<acme:submit code="lecturer.course.form.button.delete" action="/lecturer/course/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.course.form.button.create" action="/lecturer/course/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>