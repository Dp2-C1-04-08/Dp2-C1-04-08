<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textarea code="any.course.form.label.title" path="title"/>	
	<acme:input-textbox code="any.course.form.label.code" path="code"/>
	<acme:input-textarea code="any.course.form.label.course-abstract" path="courseAbstract"/>
	<acme:input-textbox code="any.course.form.label.course-type" path="courseType"/>
	<acme:input-money code="any.course.form.label.retail-price" path="retailPrice"/>
	<acme:input-url code="any.course.form.label.link" path="link"/>
</acme:form>
<jstl:choose>
		<jstl:when test="${_command == 'show' || _command == list}">
			
			<acme:button code="any.course.form.list.button" action="/any/practicum/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			
		</jstl:when>
	</jstl:choose>