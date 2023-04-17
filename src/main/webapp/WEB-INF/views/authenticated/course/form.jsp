<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textarea code="authenticated.course.form.label.title" path="title"/>	
	<acme:input-textbox code="authenticated.course.form.label.code" path="code"/>
	<acme:input-textarea code="authenticated.course.form.label.course-abstract" path="courseAbstract"/>
	<acme:input-textbox code="authenticated.course.form.label.course-type" path="courseType"/>
	<acme:input-money code="authenticated.course.form.label.retail-price" path="retailPrice"/>
	<acme:input-url code="authenticated.course.form.label.link" path="link"/>
</acme:form>
<jstl:choose>
		<jstl:when test="${_command == 'show' || _command == list}">
			
			<acme:button code="authenticated.course.form.list.button" action="/authenticated/practicum/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			
		</jstl:when>
	</jstl:choose>