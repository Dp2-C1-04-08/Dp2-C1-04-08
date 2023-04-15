
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<jstl:set var="isDraft" value="${draft }"/>

	
<acme:form readonly="${isDraft == false }">
	<acme:input-textbox code="lecturer.course.form.label.code" path="code" placeholder="[A-Z]{1,3}[0-9][0-9]{3}"/>
	<acme:input-textbox code="lecturer.course.form.label.title" path="title"/>
	<acme:input-textarea code="lecturer.course.form.label.goals" path="goals"/>
	<acme:input-textarea code="lecturer.course.form.label.course-abstract" path="courseAbstract"/>
	<acme:input-textbox code="lecturer.course.form.label.course-type" path="courseType"/>
	<acme:input-textbox code="lecturer.course.form.label.retail-price" path="retailPrice"/>
	<acme:input-url code="lecturer.course.form.label.course-link" path="link"/>
	<acme:input-checkbox code="lecturer.course.form.label.draft" path="draft"  />
</acme:form>