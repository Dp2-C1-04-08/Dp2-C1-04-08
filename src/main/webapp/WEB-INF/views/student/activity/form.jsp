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


<acme:form>
	<acme:input-textbox readonly="${false}" code="student.activity.form.label.title" path="title"/>	
	<acme:input-textbox readonly="${false}" code="student.activity.form.label.activityAbstract" path="activityAbstract"/>
	<acme:input-select readonly="${false}" code="student.activity.form.label.activityType" path="activityType" choices="${activityTypes}"/>
	<acme:input-moment readonly="${false}" code="student.activity.form.label.startTime" path="startTime"/>
	<acme:input-moment readonly="${false}" code="student.activity.form.label.endTime" path="endTime"/>
	<acme:input-url code="student.activity.form.label.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' || _command == 'update'|| _command == 'delete'}">
			<jstl:choose>
				<jstl:when test="${finalised == true}">
					<acme:submit code="student.activity.form.button.update" action="/student/activity/update"/>
					<acme:submit code="student.activity.form.button.delete" action="/student/activity/delete"/>							
				</jstl:when>
			</jstl:choose>
		</jstl:when>
		<jstl:when test="${_command == 'create' && finalised==true}"> 
			<acme:submit code="student.activity.form.button.createActivity" action="/student/activity/create?masterId=${masterId}"/>
		</jstl:when>
	</jstl:choose>
		

</acme:form>
