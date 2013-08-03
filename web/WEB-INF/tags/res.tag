<%@ tag pageEncoding="UTF-8" body-content="tagdependent" trimDirectiveWhitespaces="true"%>
<%@ tag description="CSSライブラリ引用短縮"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%-- 属性と変数の定義 --%>
<%@ attribute name="files" required="true"%>
<%@ attribute name="type" required="true"%>
<%@ attribute name="isLocal" required="false"%>
<c:set var="path">/resources${isLocal.equalsIgnoreCase('true')?packageUrl:"/common"}</c:set>
<c:forEach items="${fn:split(files, '\\\\,')}" var="file">
	<c:set var="f" value="${fn:trim(file)}" />
	<c:choose>
		<c:when test="${type.equalsIgnoreCase('css')}">
			<link href='<c:url value="${path}/css/${f}.css"/>' rel="stylesheet">
		</c:when>
		<c:when test="${type.equalsIgnoreCase('js')}">
			<script src="<c:url value='${path}/js/${f}${(f.endsWith(".jsp"))?"":".js" }'/>"></script>
		</c:when>
		<c:when test="${type.equalsIgnoreCase('img')}">
			<img src="<c:url value='${path}/img/${f}'/>" <jsp:doBody /> />
		</c:when>
	</c:choose>
</c:forEach>
