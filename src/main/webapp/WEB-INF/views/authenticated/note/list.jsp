<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.note.list.label.creationDate" path="creationDate" />
	<acme:list-column code="authenticated.note.list.label.title" path="title" />
	<acme:list-column code="authenticated.note.list.label.message" path="message" />
	<acme:list-column code="authenticated.note.list.label.email" path="email" />
	<acme:list-column code="authenticated.note.list.label.author" path="author" />
	<acme:list-column code="authenticated.note.list.label.link" path="link" />
	
</acme:list>


	
 