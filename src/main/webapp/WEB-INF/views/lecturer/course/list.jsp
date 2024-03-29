
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.course.list.label.code" path="code" width="20%"/>
	<acme:list-column code="lecturer.course.list.label.title" path="title" width="20%"/>
	<acme:list-column code="lecturer.course.list.label.course-abstract" path="courseAbstract" width="60%"/>
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="lecturer.course.list.button.create" action="/lecturer/course/create"/>
</jstl:if>