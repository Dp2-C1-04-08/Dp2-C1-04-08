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
	<acme:message code="auditor.dashboard.form.title"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.numberOfAuditsForTheoreticalCourses"/>
		</th>
		<td>
			<acme:print value="${numberOfAuditsForTheoreticalCourses}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.numberOfAuditsForHandsOnCourses"/>
		</th>
		<td>
			<acme:print value="${numberOfAuditsForHandsOnCourses}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.averageNumberOfRecordsInAudit"/>
		</th>
		<td>
			<acme:print value="${averageNumberOfRecordsInAudit}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.deviationNumberOfRecordsInAudit"/>
		</th>
		<td>
			<acme:print value="${deviationNumberOfRecordsInAudit}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.maximumNumberOfRecordsInAudit"/>
		</th>
		<td>
			<acme:print value="${maximumNumberOfRecordsInAudit}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.minimumNumberOfRecordsInAudit"/>
		</th>
		<td>
			<acme:print value="${minimumNumberOfRecordsInAudit}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.averageLengthOfPeriodInRecords"/>
		</th>
		<td>
			<acme:print value="${averageLengthOfPeriodInRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.deviationLengthOfPeriodInRecords"/>
		</th>
		<td>
			<acme:print value="${deviationLengthOfPeriodInRecords}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.maximumLengthOfPeriodInRecords"/>
		</th>
		<td>
			<acme:print value="${maximumLengthOfPeriodInRecords}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="auditor.dashboard.form.label.minimumLengthOfPeriodInRecords"/>
		</th>
		<td>
			<acme:print value="${minimumLengthOfPeriodInRecords}"/>
		</td>
	</tr>			
</table>
<acme:return/>






