<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="jp">
<head>
<meta charset="utf-8">
<title>SSM</title>
<meta name="description" content="spring mvc mybatis sample.">
<s:res type="css" files="bootstrap, datepicker, ssm" />
<link rel="shortcut icon" href="<c:url value='/resources/common/img/favicon.png'/>">
<script src="<c:url value='/resources/common/js/serversidejs.jsp'/>"></script>
<s:res type="js"
	files="jquery-2.0.2, jquery-ui-1.10.3.custom, jquery.form, bootstrap, bootstrap-datepicker, bootstrap-datepicker.ja, ssm" />
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
	<s:res type="js" files="html5shiv" />
<![endif]-->
</head>

<body>
	<%-- ナビバー --%>
	<%@include file="navbar.jsp"%>

	<%-- メイン --%>
	<div class="container-fluid">

		<%-- エラーメッセージ --%>
		<%@include file="messages.jsp"%>

		<%--内容 --%>
		<div class="row-fluid">
			<jsp:include page="${subview}" />
		</div>

		<hr>
		<footer>
			<p>&copy; CLXY Studio 2013</p>
		</footer>

	</div>
</body>
</html>
