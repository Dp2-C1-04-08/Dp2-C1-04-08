
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.system-configuration.form.label.system-currency" path="systemCurrency" />
	<acme:input-textbox code="administrator.system-configuration.form.label.accepted-currencies" path="acceptedCurrencies" placeholder="administrator.system-configuration.form.placeholder.accepted-currencies"/>
	
	<acme:submit code="administrator.system-configuration.form.button.update" action="/administrator/system-configuration/update"/>
			
</acme:form>