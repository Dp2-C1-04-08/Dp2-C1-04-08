<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.peep.list.label.instanciationMoment" path="instanciationMoment" width="10%"/>
	<acme:list-column code="authenticated.peep.list.label.nick" path="nick" width="10%"/>
	<acme:list-column code="authenticated.peep.list.label.title" path="title" width="20%"/>
	<acme:list-column code="authenticated.peep.list.label.message" path="message" width="20%"/>
	<acme:list-column code="authenticated.peep.list.label.email" path="email" width="20%"/>
	<acme:list-column code="authenticated.peep.list.label.link" path="link" width="20%"/>
</acme:list>