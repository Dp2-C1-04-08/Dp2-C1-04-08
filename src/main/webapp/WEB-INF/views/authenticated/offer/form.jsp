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
	<acme:input-textbox code="authenticated.offer.form.label.heading" path="heading"/>
	<acme:input-textarea code="authenticated.offer.form.label.summary" path="summary"/>
	<acme:input-money code="authenticated.offer.form.label.price" path="price"/>
	<acme:input-url code="authenticated.offer.form.label.link" path="link"/>
	<acme:input-moment code="authenticated.offer.form.label.start-date" path="startDate"/>
	<acme:input-moment code="authenticated.offer.form.label.end-date" path="endDate"/>
</acme:form>
