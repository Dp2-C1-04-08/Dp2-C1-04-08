<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.course.form.label.title" path="title"/>	
	<acme:input-textarea code="any.course.form.label.code" path="code"/>
	<acme:input-textbox code="any.course.form.label.course-abstract" path="course-abstract"/>
	<acme:input-textarea code="any.course.form.label.course-type" path="course-type"/>
	<acme:input-money code="any.course.form.label.retail-price" path="retail-price"/>
	<acme:input-url code="any.course.form.label.link" path="link"/>
</acme:form>