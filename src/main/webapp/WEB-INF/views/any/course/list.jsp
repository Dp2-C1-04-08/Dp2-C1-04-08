<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.course.list.label.title" path="title" width="20%"/>
	<acme:list-column code="any.course.list.label.code" path="code" width="20%"/>
	<acme:list-column code="any.course.list.label.course-abstract" path="courseAbstract" width="20%"/>
	<acme:list-column code="any.course.list.label.course-type" path="courseType" width="20%"/>
	<acme:list-column code="any.course.list.label.retail-price" path="retailPrice" width="10%"/>
	<acme:list-column code="any.course.list.label.link" path="link" width="10%"/>
</acme:list>