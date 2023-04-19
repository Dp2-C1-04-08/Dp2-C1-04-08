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
	<acme:message code="company.dashboard.form.title"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.averagePractica"/>
		</th>
		<td>
			<acme:print value="${averagePractica}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.deviationPractica"/>
		</th>
		<td>
			<acme:print value="${deviationPractica}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.maxPractica"/>
		</th>
		<td>
			<acme:print value="${maximumPractica}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.minPractica"/>
		</th>
		<td>
			<acme:print value="${minimumPractica}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.averageSession"/>
		</th>
		<td>
			<acme:print value="${averageSession}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.deviationSession"/>
		</th>
		<td>
			<acme:print value="${deviationSession}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.maxSession"/>
		</th>
		<td>
			<acme:print value="${maximumSession}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.minSession"/>
		</th>
		<td>
			<acme:print value="${minimumSession}"/>
		</td>
	</tr>	
</table>

<div>

<span style="font-size: 150%; font-weight: bold;"><acme:message code="company.dashboard.form.chart.theoretical"/></span>

	<canvas id="canvas"></canvas>

		
</div>
<div>
	<span style="font-size: 150%; font-weight: bold;"><acme:message code="company.dashboard.form.chart.handsOn"/></span>
	<canvas id="canvas2"></canvas>
		
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${theoreticalPracticum.get(0)}"/>, 
						<jstl:out value="${theoreticalPracticum.get(1)}"/>, 
						<jstl:out value="${theoreticalPracticum.get(2)}"/>,
						<jstl:out value="${theoreticalPracticum.get(3)}"/>,
						<jstl:out value="${theoreticalPracticum.get(4)}"/>,
						<jstl:out value="${theoreticalPracticum.get(5)}"/>,
						<jstl:out value="${theoreticalPracticum.get(6)}"/>,
						<jstl:out value="${theoreticalPracticum.get(7)}"/>,
						<jstl:out value="${theoreticalPracticum.get(8)}"/>,
						<jstl:out value="${theoreticalPracticum.get(8)}"/>,
						<jstl:out value="${theoreticalPracticum.get(10)}"/>,
						<jstl:out value="${theoreticalPracticum.get(11)}"/>,
						
					]
				
				}
			]
		};
		var data2 = {
				labels : [
						"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
				],
				datasets : [
					{
					data:[
							<jstl:out value="${handsOnPracticum.get(0)}"/>, 
							<jstl:out value="${handsOnPracticum.get(1)}"/>, 
							<jstl:out value="${handsOnPracticum.get(2)}"/>,
							<jstl:out value="${handsOnPracticum.get(3)}"/>,
							<jstl:out value="${handsOnPracticum.get(4)}"/>,
							<jstl:out value="${handsOnPracticum.get(5)}"/>,
							<jstl:out value="${handsOnPracticum.get(6)}"/>,
							<jstl:out value="${handsOnPracticum.get(7)}"/>,
							<jstl:out value="${handsOnPracticum.get(8)}"/>,
							<jstl:out value="${handsOnPracticum.get(9)}"/>,
							<jstl:out value="${handsOnPracticum.get(10)}"/>,
							<jstl:out value="${handsOnPracticum.get(11)}"/>,]
					
					}
				]
			};
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 1.0
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context, canvas2;
	
		canvas = document.getElementById("canvas");
		canvas2 = document.getElementById("canvas2");
		context = canvas.getContext("2d");
		context2 = canvas2.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
		
		new Chart(context2 , {
			type : "bar",
			data : data2,
			options : options
		});
	});
</script>

<acme:return/>






