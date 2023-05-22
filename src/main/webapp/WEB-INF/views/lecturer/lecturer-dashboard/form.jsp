<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="lecturer.dashboard.form.title"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.HandsOnCount"/>
		</th>
		<td>
			<acme:print value="${handsOnLectureCount}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.TheoreticalCount"/>
		</th>
		<td>
			<acme:print value="${theoreticalLectureCount}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.averageLecture"/>
		</th>
		<td>
			<acme:print value="${averageLecture}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.deviationLecture"/>
		</th>
		<td>
			<acme:print value="${deviationLecture}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.minimumLecture"/>
		</th>
		<td>
			<acme:print value="${minimumLecture}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.maximumLecture"/>
		</th>
		<td>
			<acme:print value="${maximumLecture}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.averageCourse"/>
		</th>
		<td>
			<acme:print value="${averageCourse}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.deviationCourse"/>
		</th>
		<td>
			<acme:print value="${deviationCourse}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.minimumCourse"/>
		</th>
		<td>
			<acme:print value="${minimumCourse}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="lecturer.dashboard.form.label.maximumCourse"/>
		</th>
		<td>
			<acme:print value="${maximumCourse}"/>
		</td>
	</tr>		
</table>
<acme:return/>

