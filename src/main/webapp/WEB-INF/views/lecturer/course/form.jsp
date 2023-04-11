
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="lecturer.course.form.label.code" path="code" />
	<acme:input-textbox code="lecturer.course.form.label.title" path="title" />
	<acme:input-textarea code="lecturer.course.form.label.course-abstract" path="courseAbstract" />
	<acme:input-textarea code="lecturer.course.form.label.course-type" path="courseType" />
	<acme:input-textarea code="lecturer.course.form.label.retail-price" path="retailPrice" />
	<acme:input-textarea code="lecturer.course.form.label.link" path="link" />
</acme:form>