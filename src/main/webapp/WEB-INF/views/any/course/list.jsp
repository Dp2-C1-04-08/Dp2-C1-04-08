<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.course.list.label.title" path="title" width="20%"/>
	<acme:list-column code="any.course.list.label.code" path="code" width="20%"/>
	<acme:list-column code="any.course.list.label.course-abstract" path="course-abstract" width="20%"/>
	<acme:list-column code="any.course.list.label.course-type" path="course-type" width="20%"/>
	<acme:list-column code="any.course.list.label.retail-price" path="retail-price" width="10%"/>
	<acme:list-column code="any.course.list.label.link" path="link" width="10%"/>
</acme:list>