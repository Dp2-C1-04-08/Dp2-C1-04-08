<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.lecture.list.label.title" path="title" width="20%"/>
	<acme:list-column code="lecturer.lecture.list.label.lecture-abstract" path="lectureAbstract" width="60%"/>
	<acme:list-column code="lecturer.lecture.list.label.estimated-learning-time" path="estimatedLearningTime" width="20%"/>
</acme:list>


<jstl:if test="${_command == 'list'}">
	<acme:button code="lecturer.lecture.list.button.create" action="/lecturer/lecture/create?masterId=${id}"/>
</jstl:if>