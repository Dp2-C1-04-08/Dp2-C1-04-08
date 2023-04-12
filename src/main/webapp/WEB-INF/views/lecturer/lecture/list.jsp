<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.lecture.list.label.title" path="title" width="20%"/>
	<acme:list-column code="lecturer.lecture.list.label.lecture-abstract" path="lectureAbstract" width="20%"/>
	<acme:list-column code="lecturer.lecture.list.label.lecture-type" path="lectureType" width="10%"/>
	<acme:list-column code="lecturer.lecture.list.label.body" path="body" width="20%"/>
	<acme:list-column code="lecturer.lecture.list.label.estimated-learning-time" path="estimatedLearningTime" width="20%"/>
	<acme:list-column code="lecturer.lecture.list.label.link" path="link" width="10%"/>
</acme:list>