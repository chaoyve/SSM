<%@page pageEncoding="UTF-8"%>
<style type="text/css">
.form-horizontal {
	max-width: 450px;
}
</style>
<form:form method="post" class="form-horizontal" action="doRegister" commandName="auth">
	<h2>
		<spring:message code="auth.title.register" />
	</h2>
	<div class="control-group">
		<label class="control-label" for="userId">
			<spring:message code='auth.userId'/>
		</label>
		<div class="controls">
			<form:input path="userId"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="firstName">
			<spring:message code='auth.firstName'/>
		</label>
		<div class="controls">
			<form:input path="firstName"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="lastName">
			<spring:message code='auth.lastName'/>
		</label>
		<div class="controls">
			<form:input path="lastName"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="password">
			<spring:message code='auth.password'/>
		</label>
		<div class="controls">
			<form:password path="password"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="confirmPassword">
			<spring:message code='auth.confirmPassword'/>
		</label>
		<div class="controls">
			<form:password path="confirmPassword"/>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label" for="birthday">
			<spring:message code='auth.birthday'/>
		</label>
		<div class="controls">
			<div class="input-append date" id="birthdayDate">
				<form:input path="birthday" cssClass="span9"/>
				<a class='btn add-on'><i class="icon-calendar"></i></a>
			</div>
		</div>
		<s:dateInput target="birthdayDate"/>			
	</div>
	<hr>
	<div class="text-center row-fluid span12">
		<span class="span6">
			<button class="btn btn-primary" data-loading-text="Loading...">送信</button>
		</span>
		<span class="span6">
			<button class="btn" type="button" data-toggle="modal" data-target="#myModal">送信</button>
		</span>
	</div>
	<br/>
</form:form>

<div id="myModal" class="modal hide fade in" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
	aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3 id="myModalLabel">Modal header</h3>
	</div>
	<div class="modal-body">
		<p>One fine body…</p>
	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
		<button class="btn btn-primary">Save changes</button>
	</div>
</div>