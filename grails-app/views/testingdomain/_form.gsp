<%@ page import="com.devops.testing.Testingdomain" %>



<div class="fieldcontain ${hasErrors(bean: testingdomainInstance, field: 'address', 'error')} ">
	<label for="address">
		<g:message code="testingdomain.address.label" default="Address" />
		
	</label>
	<g:textField name="address" value="${testingdomainInstance?.address}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: testingdomainInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="testingdomain.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${testingdomainInstance?.name}"/>
</div>

