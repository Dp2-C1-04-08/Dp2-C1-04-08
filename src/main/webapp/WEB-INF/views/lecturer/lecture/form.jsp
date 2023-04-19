<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<jstl:set var="isDraft" value="${draft }"/>

	
<acme:form readonly="${isDraft == false }">
	<acme:input-textarea code="lecturer.lecture.form.label.title" path="title"/>
	<acme:input-textarea code="lecturer.lecture.form.label.lecture-abstract" path="lectureAbstract"/>
	<acme:input-double code="lecturer.lecture.form.label.estimated-learning-time" path="estimatedLearningTime"/>
	<acme:input-textarea code="lecturer.lecture.form.label.body" path="body"/>
	<acme:input-textbox code="lecturer.lecture.form.label.lecture-type" path="lectureType"/>
	<acme:input-url code="lecturer.lecture.form.label.link" path="link"/>
	
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' || _command == 'update'|| _command == 'delete'}">
			<acme:input-checkbox code="lecturer.lecture.form.label.draft" path="draft"/>
			<acme:submit code="lecturer.lecture.form.button.update" action="/lecturer/lecture/update"/>
			<acme:submit code="lecturer.lecture.form.button.delete" action="/lecturer/lecture/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.lecture.form.button.create" action="/lecturer/lecture/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>