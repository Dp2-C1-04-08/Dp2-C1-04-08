
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

	
<acme:form readonly="${draft == false }">
	<acme:input-textbox code="lecturer.course.form.label.code" path="code" placeholder="[A-Z]{1,3}[0-9][0-9]{3}"/>
	<acme:input-textbox code="lecturer.course.form.label.title" path="title"/>
	<acme:input-textarea code="lecturer.course.form.label.course-abstract" path="courseAbstract"/>
	<acme:input-money code="lecturer.course.form.label.retail-price" path="retailPrice"/>
	<acme:input-url code="lecturer.course.form.label.link" path="link"/>
	<acme:input-textbox code="lecturer.course.form.label.course-type" path="courseType" readonly="true"/>
	<acme:input-checkbox code="lecturer.course.form.label.draft" path="draft" readonly="true"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' && draft == false}">
			<acme:button code="lecturer.lecture.list.button" action="/lecturer/lecture/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draft == true}">
			<acme:submit code="lecturer.course.form.button.update" action="/lecturer/course/update"/>
			<acme:submit code="lecturer.course.form.button.delete" action="/lecturer/course/delete"/>
			<acme:submit code="lecturer.course.form.button.publish" action="/lecturer/course/publish"/>
			<acme:button code="lecturer.lecture.list.button" action="/lecturer/lecture/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="lecturer.course.form.button.create" action="/lecturer/course/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>