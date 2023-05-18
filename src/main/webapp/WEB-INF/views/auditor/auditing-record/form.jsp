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
	<acme:input-textbox code="auditor.auditingRecord.form.label.subject" path="subject"/>
	<acme:input-textbox code="auditor.auditingRecord.form.label.assessment" path="assessment"/>
	<acme:input-moment code="auditor.auditingRecord.form.label.startDate" path="startDate"/>
	<acme:input-moment code="auditor.auditingRecord.form.label.endDate" path="endDate"/>
	<acme:input-select code="auditor.auditingRecord.form.label.mark" path="mark" choices="${marks}"/>
	<acme:input-url code="auditor.auditingRecord.form.label.link" path="link"/>
	<acme:input-checkbox code="auditor.auditingRecord.form.label.correction" path="correction" readonly="true"/>
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<jstl:if test="${published}">
				<acme:input-checkbox code="auditor.auditingRecord.form.label.confirmation" path="confirmation"/>
			</jstl:if>
			<acme:submit code="auditor.auditingRecord.form.button.create" action="/auditor/auditing-record/create?masterId=${masterId}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && !published}">
			<acme:submit code="auditor.auditingRecord.form.button.update" action="/auditor/auditing-record/update"/>
			<acme:submit code="auditor.auditingRecord.form.button.delete" action="/auditor/auditing-record/delete"/>
		</jstl:when>
	</jstl:choose>
</acme:form>