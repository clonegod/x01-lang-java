<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
String ctx = request.getContextPath();
%>

<html lang="en">

<head>
	<link rel="stylesheet" href="<%=ctx%>/static/css/main.css" type="text/css" />
</head>

<body>
	<h1>welcome.jsp</h1>
	<!-- 声明一个url变量 -->
	<c:url value="/resources/text.txt" var="url"/>
	<spring:url value="/resources/text.txt" htmlEscape="true" var="springUrl" />
	
	Spring URL: ${springUrl} at ${time}
	<br>
	
	JSTL URL: ${url}
	<br>
	
	Message: ${message}
</body>

</html>