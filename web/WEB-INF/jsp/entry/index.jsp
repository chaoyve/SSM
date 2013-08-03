<%@page pageEncoding="UTF-8"%>
<div class="hero-unit">
	<h1>Hello, world!</h1>
	<p>This is a template for a simple marketing or informational website. It includes a large
		callout called the hero unit and three supporting pieces of content. Use it as a starting point to
		create something more unique.</p>
	<p>
		<a href="#" class="btn btn-primary btn-large">Learn more &raquo;</a>
	</p>
</div>
<div class="row-fluid">
	<div class="span4">
		<h2>In</h2>
		<p>
			Welcome. <br>
			<c:if test="${user.logined }">${user.firstName }</c:if>
			<c:if test="${!user.logined }">
				<a href="<c:url value='/auth/'/>">ログインしてください</a>
			</c:if>
		</p>
		<p>
			<a class="btn" href="#">View details &raquo;</a>
		</p>
	</div>
	<div class="span4">
		<h2>Out</h2>
		<p>A lot of those will out today.</p>
		<p>
			<a class="btn" href="#">View details &raquo;</a>
		</p>
	</div>
	<div class="span4">
		<h2>Move</h2>
		<p>A lot of those will move today.</p>
		<p>
			<a class="btn" href="#">View details &raquo;</a>
		</p>
	</div>
	<!--/span-->
</div>
<div class="row-fluid">
	<div class="span4">
		<h2>Process</h2>
		<p>A lot of those will process today.</p>
		<p>
			<a class="btn" href="#">View details &raquo;</a>
		</p>
	</div>
	<div class="span4">
		<h2>Other</h2>
		<p>Work, work.</p>
		<p>
			<a class="btn" href="#">View details &raquo;</a>
		</p>
	</div>
</div>
