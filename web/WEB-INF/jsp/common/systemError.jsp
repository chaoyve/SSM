<%@page pageEncoding="UTF-8" isErrorPage="true" session="false"%>
<%@page import="cn.clxy.ssm.common.util.WebUtil"%>
<%
	WebUtil.invalidate();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<title>System Exception</title>
<s:res type="css" files="bootstrap, datepicker, ssm" />
<link rel="shortcut icon" href="<c:url value='/resources/common/img/favicon.png'/>">
<style type="text/css" media="screen">
.container {
	margin: 10px auto 40px auto;
	width: 600px;
	text-align: center;
}

#suggestions {
	margin-top: 15px;
	color: #ccc;
}

#suggestions a {
	font-weight: 200;
	font-size: 14px;
	margin: 0 10px;
}
</style>
</head>
<body>
	<div class="container">
		<h1>エラー！エラー！</h1>
		<br><br>
		<p>
			<strong>システムエラーが発生しました。</strong>
		</p>
		<br>
		<p>
			当時利用状況や操作内容を含めて、システム管理者を連絡してください。<br>ご迷惑をかけまして申し訳ございません。
		</p>
		<br>
		<div id="suggestions">
			<a href="#">Contact Support1</a> &mdash;
			<a href=""">Contact Support2</a> &mdash;
			<a href="#">Contact Support3</a>
		</div>
	</div>
</body>
</html>
