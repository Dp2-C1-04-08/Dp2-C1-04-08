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

<h2>
	<acme:message code="student.dashboard.form.title"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.numberOfTheoreticalActivities"/>
		</th>
		<td>
			<acme:print value="${numberOfTheoreticalActivities}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.numberOfHandsOnActivities"/>
		</th>
		<td>
			<acme:print value="${numberOfHandsOnActivities}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.averagePeriodOfActivities"/>
		</th>
		<td>
			<acme:print value="${averagePeriodOfActivities}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.deviationPeriodOfActivities"/>
		</th>
		<td>
			<acme:print value="${deviationPeriodOfActivities}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.maximumPeriodOfActivities"/>
		</th>
		<td>
			<acme:print value="${maximumPeriodOfActivities}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.minimumPeriodOfActivities"/>
		</th>
		<td>
			<acme:print value="${minimumPeriodOfActivities}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.averageLearningTime"/>
		</th>
		<td>
			<acme:print value="${averageLearningTime}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.deviationLearningTime"/>
		</th>
		<td>
			<acme:print value="${deviationLearningTime}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.maximumLearningTime"/>
		</th>
		<td>
			<acme:print value="${maximumLearningTime}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.minimumLearningTime"/>
		</th>
		<td>
			<acme:print value="${minimumLearningTime}"/>
		</td>
	</tr>			
</table>
<acme:return/>






