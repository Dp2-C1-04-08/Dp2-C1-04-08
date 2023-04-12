<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textarea code="lecturer.lecture.form.label.title" path="title"/>
	<acme:input-textarea code="lecturer.lecture.form.label.lecture-abstract" path="lectureAbstract"/>
	<acme:input-double code="lecturer.lecture.form.label.estimated-learning-time" path="estimatedLearningTime"/>
	<acme:input-textarea code="lecturer.lecture.form.label.body" path="body"/>
	<acme:input-textbox code="lecturer.lecture.form.label.lecture-type" path="lectureType"/>
	<acme:input-url code="lecturer.lecture.form.label.link" path="link"/>
</acme:form>