<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false" %>
<html>
  <head>
    <title>Spitter</title>
    <link rel="stylesheet" type="text/css" 
          href="<c:url value="/resources/style.css" />" >
  </head>
  <body>
    <h1>Register - JSR303 @Valid example</h1>
    <sf:form method="POST" modelAttribute="spitterForm"
    			enctype="multipart/form-data">
      
      <sf:errors path="*" cssClass="errorblock" element="div" />
      
      <table>
			<tr>
				<td><sf:label path="firstName" cssErrorClass="error">First Name:</sf:label></td>
				<td><sf:input type="text" path="firstName" /></td>
				<td><sf:errors path="firstName" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Last Name :</td>
				<td><sf:input type="text" path="lastName" /></td>
				<td><sf:errors path="lastName" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Email :</td>
				<td><sf:input type="email" path="email" /></td>
				<td><sf:errors path="email" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Username :</td>
				<td><sf:input type="text" path="username" /></td>
				<td><sf:errors path="username" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Password :</td>
				<td><sf:input type="password" path="password" /></td>
				<td><sf:errors path="password" cssClass="error" /></td>
			</tr>
			<tr>
				<td>Password :</td>
				<td><sf:input type="file" path="profilePicture" /></td>
				<td><sf:errors path="profilePicture" cssClass="error" /></td>
			</tr>
			<tr>
				<td colspan="3"><input type="submit" value="Register" /></td>
			</tr>
		</table>
    </sf:form>
  </body>
</html>
