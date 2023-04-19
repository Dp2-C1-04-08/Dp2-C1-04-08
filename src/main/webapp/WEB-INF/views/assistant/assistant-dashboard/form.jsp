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
	<acme:message code="assistant.dashboard.form.title"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.HandsOnCount"/>
		</th>
		<td>
			<acme:print value="${handsOnTutorialCount}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.TheoreticalCount"/>
		</th>
		<td>
			<acme:print value="${theoreticalTutorialCount}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.tutorialsAverage"/>
		</th>
		<td>
			<acme:print value="${tutorialsAverage}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.tutorialsDeviation"/>
		</th>
		<td>
			<acme:print value="${tutorialsDeviation}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.tutorialsMinimum"/>
		</th>
		<td>
			<acme:print value="${tutorialsMinimum}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.tutorialsMaximum"/>
		</th>
		<td>
			<acme:print value="${tutorialsMaximum}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.sessionsAverage"/>
		</th>
		<td>
			<acme:print value="${sessionsAverage}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.sessionsDeviation"/>
		</th>
		<td>
			<acme:print value="${sessionsDeviation}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.sessionsMinimum"/>
		</th>
		<td>
			<acme:print value="${sessionsMinimum}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="assistant.dashboard.form.label.sessionsMaximum"/>
		</th>
		<td>
			<acme:print value="${sessionsMaximum}"/>
		</td>
	</tr>		
</table>
<acme:return/>






