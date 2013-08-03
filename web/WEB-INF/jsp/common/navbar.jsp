<%@page pageEncoding="UTF-8"%>
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<a href="<c:url value='/'/>" class="brand">
				SSM&nbsp;-&nbsp;<small>Spring MVC Mybatis</small>
			</a>
			<div class="nav-collapse collapse">
				<c:if test="${user.logined }">
					<p class="navbar-text pull-right">
						こんにちは${user.firstName} 
						<a href="<c:url value='/logout'/>" class="navbar-link">ログアウト</a>
					</p>
				</c:if>
				<ul class="nav">
					<c:if test="${user.logined }">
						<li class="active"><a href="#">Home</a></li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								In<b class="caret"></b>
							</a>
							<ul class="dropdown-menu">
								<li><a href="#">Action</a></li>
								<li><a href="#">Another action</a></li>
								<li><a href="#">Something else here</a></li>
								<li class="divider"></li>
								<li class="nav-header">Nav header</li>
								<li><a href="#">Separated link</a></li>
								<li><a href="#">One more separated link</a></li>
							</ul>
						</li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								Out<b class="caret"></b>
							</a>
							<ul class="dropdown-menu">
								<li><a href="#">Action</a></li>
								<li><a href="#">Another action</a></li>
								<li><a href="#">Something else here</a></li>
								<li class="divider"></li>
								<li class="nav-header">Nav header</li>
								<li><a href="#">Separated link</a></li>
								<li><a href="#">One more separated link</a></li>
							</ul></li>
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								In/Out<b class="caret"></b>
							</a>
							<ul class="dropdown-menu">
								<li><a href="#">Action</a></li>
								<li><a href="#">Another action</a></li>
								<li><a href="#">Something else here</a></li>
								<li class="divider"></li>
								<li class="nav-header">Nav header</li>
								<li><a href="#">Separated link</a></li>
								<li><a href="#">One more separated link</a></li>
							</ul></li>
					</c:if>
					<li>
						<div class="btn-group">
							<a href="<c:url value='/wow/'/>" class="btn btn-success">
								<s:res type="img" files="wow.png">alt="wow" style="height: 25px; width: 25px;"</s:res>
								&nbsp;WOW
							</a>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>
