<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<html>
  <head>
    <title>Spitter</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css" />" >
  </head>
  <body>
    <div class="spittleForm">
      <h1>Spit it out...</h1>
      <sf:form method="POST" modelAttribute="spittleForm">
      	<sf:errors path="*" cssClass="errorblock" element="div" />
        <sf:hidden path="latitude" />
        <sf:hidden path="longitude" />
        <sf:textarea path="message" cols="80" rows="5"></sf:textarea><br/>
        <input type="submit" value="Add" />
      </sf:form>
    </div>
    
    <div class="listTitle">
      <h1>Recent Spittles</h1>
      
      <ul class="spittleList">
        <c:forEach items="${spittleList}" var="spittle" >
          <li id="spittle_<c:out value="spittle.id"/>">
	        <s:url value="/spittles/${spittle.id}" var="spittle_url" />
            <a href="${spittle_url}"><c:out value="${spittle.message}" /></a>
            <div>
              <span class="spittleTime"><c:out value="${spittle.time}" /></span>
              <span class="spittleLocation">(<c:out value="${spittle.latitude}" />, <c:out value="${spittle.longitude}" />)</span>
            </div>
          </li>
        </c:forEach>
      </ul>
      
      <c:if test="${fn:length(spittleList) gt 20}">
        <hr />
        <s:url value="/spittles?count=${nextCount}" var="more_url" />
        <a href="${more_url}">Show more</a>
      </c:if>
    </div>
  </body>
</html>