<%@page pageEncoding="UTF-8"%>
<style type="text/css">
	.form-signin {
		max-width: 380px;
	}

	input[type="text"],input[type="password"] {
		font-size: 16px;
		height: auto;
		margin-bottom: 15px;
		padding: 7px 9px;
	}
</style>
<div class="hero-unit span6">
	<h1>SSM</h1>
	<br>
	<p>
		<a href="http://www.springsource.org/">Spring MVC with Spring</a>,
		<a href="https://code.google.com/p/mybatis/‎">MyBatis</a>.
	</p>
	<p>
		<a href="http://jxls.sourceforge.net/‎">jXLS</a> with
		<a href="http://poi.apache.org‎">POI </a>,
		<a href="http://www.hibernate.org/subprojects/validator.html">Hibernate Validator</a>,
		<a href="http://jackson.codehaus.org/">Jackson</a>.		
	</p>
	<p>
		<a href="http://twitter.github.io/bootstrap/index.html">Bootstrap</a> with 
		<a href="http://glyphicons.com/">Glyphicons</a>.
		<a href="#modalDiv" data-toggle="modal" class="btn">Modal Sample</a>
		<button type="button" class="btn" onclick="ssm.dialog({callback:function(){console.log('OK is clicked.')},body:'Test:' + new Date()});">Modal Sample2</button>
	</p>
	<p>
		<a href="http://jquery.com/">jQuery</a>, 
		<a href="http://www.eyecon.ro/bootstrap-datepicker/">Datepicker for Bootstrap</a>,
		<a href="https://github.com/Mottie/tablesorter">tablesorter</a>.
	</p>
	<hr>
	<p class="pull-right">Powered by &copy; CLXY Studio</p>
</div>

<form:form method="post" class="form-signin span6" action="login" commandName="auth">
	<h3>
		<spring:message code="auth.title" />
	</h3>
	<spring:message code='auth.userId' var="userIdName" />
	<spring:message code='auth.password' var="passwordName" />
	<div class="control-group">
		<div class="controls">
			<form:input path="userId" cssClass="input-block-level" placeholder="${userIdName }" />
		</div>
	</div>
	<div class="control-group">
		<div class="controls">
			<form:password path="password" cssClass="input-block-level" placeholder="${passwordName }" />
		</div>
	</div>
	<hr />
	<div class="span12 text-center">
		<span class="span6">
			<input type="submit" class="btn btn-large btn-primary" value="<spring:message code='auth.login'/>">
		</span>
		<span class="span6"">
			<a href="register" class="btn btn-large"><spring:message code="auth.title.register" /></a>
		</span>
	</div>
	<br>
	<br>
	<%--<jsr303js:validate commandName="command"/> --%>
</form:form>
<div id="jsr303jsLogDiv"></div>
