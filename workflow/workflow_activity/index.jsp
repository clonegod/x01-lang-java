<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String url = request.getRequestURL().toString();
	url = url.substring(0, url.indexOf('/', url.indexOf("//") + 2));
	String context = request.getContextPath();
	url += context;
	application.setAttribute("ctx", url);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>index</h1><a href="${ctx}/logout.html">退出</a>
	<p>验证当前用户是否为“访客”，即未认证（包含未记住）的用户</p>
	<shiro:guest>  
    	Hi there!  Please <a href="login.jsp">Login</a> or <a href="signup.jsp">Signup</a> today!  
	</shiro:guest>
	<p>认证通过或已记住的用户</p>
	<shiro:user>  
    	Welcome back John!  Not John? Click <a href="login.jsp">here<a> to login. 
	</shiro:user>
	<p>已认证通过的用户。不包含已记住的用户，这是与user标签的区别所在。</p>
	<shiro:authenticated>  
    	<a href="updateAccount.jsp">Update your contact information</a>.  
	</shiro:authenticated>
	<p>未认证通过用户，与authenticated标签相对应。与guest标签的区别是，该标签包含已记住用户。</p>
	<shiro:notAuthenticated>  
	    Please <a href="login.jsp">login</a> in order to update your credit card information.  
	</shiro:notAuthenticated>  
	<p>输出当前用户信息，通常为登录帐号信息</p>
	Hello, <shiro:principal/>, how are you today?  
	<p>验证当前用户是否属于该角色</p>
	<shiro:hasRole name="administrator">  
	    <a href="admin.jsp">Administer the system</a>  
	</shiro:hasRole>  
	<p>与hasRole标签逻辑相反，当用户不属于该角色时验证通过</p>
	<shiro:lacksRole name="administrator">  
	    Sorry, you are not allowed to administer the system.  
	</shiro:lacksRole>  
	<p>验证当前用户是否属于以下任意一个角色。</p>
	<shiro:hasAnyRoles name="developer, project manager, administrator">
	    You are either a developer, project manager, or administrator.  
	</shiro:hasAnyRoles>
	<p>验证当前用户是否拥有制定权限</p>
	<shiro:hasPermission name="user:create">  
	    <a href="createUser.jsp">Create a new User</a>  
	</shiro:hasPermission>  
	<p>与hasPermission标签逻辑相反，当前用户没有制定权限时，验证通过</p>
	<shiro:hasPermission name="user:create">  
	    <a href="createUser.jsp">Create a new User</a>  
	</shiro:hasPermission>  
</body>
</html>