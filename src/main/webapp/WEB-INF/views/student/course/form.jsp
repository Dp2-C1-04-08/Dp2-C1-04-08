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

<acme:form readonly="${true}">
	<acme:input-textbox code="student.course.form.label.code" path="code"/>
	<acme:input-textbox code="student.course.form.label.title" path="title"/>	
	<acme:input-textbox code="student.course.form.label.courseAbstract" path="courseAbstract"/>
	<acme:input-textbox code="student.course.form.label.courseType" path="courseType"/>
	<acme:input-money code="student.course.form.label.retailPrice" path="retailPrice"/>
	<acme:input-url code="student.course.form.label.link" path="link"/>
	
	<acme:input-integer code="student.course.form.label.lecturer-id" path="id"/>
	<acme:input-textbox code="student.course.form.label.lectures-title" path="title"/>
	<%--<acme:input-textbox code="student.course.form.label.lectures-lectureAbstract" path="lectureAbstract"/>
	<acme:input-textbox code="student.course.form.label.lectures-lectureType" path="lectureType"/>
	<acme:input-double code="student.course.form.label.lectures-estimatedLearningTime" path="estimatedLearningTime"/>
	<acme:input-textbox code="student.course.form.label.lectures-body" path="body"/>
	<acme:input-url code="student.course.form.label.lectures-link" path="link"/> --%>
	
	<acme:button code="student.enrolment.form.button.register" action="/student/enrolment/register?id=${id}"/>
</acme:form>
