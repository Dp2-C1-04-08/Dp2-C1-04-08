
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.course.list.label.code" path="code" width="10%"/>
	<acme:list-column code="lecturer.course.list.label.title" path="title" width="10%"/>
	<acme:list-column code="lecturer.course.list.label.course-abstract" path="courseAbstract" width="20%"/>	
	<acme:list-column code="lecturer.course.list.label.course-type" path="courseType" width="20%"/>	
	<acme:list-column code="lecturer.course.list.label.retail-price" path="courseType" width="20%"/>	
	<acme:list-column code="lecturer.course.list.label.link" path="courseType" width="20%"/>
</acme:list>
