<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<sql:query var="rs" dataSource="jdbc/TestDB">
select id, name, age from t_user
</sql:query>

<html>
  <head>
    <title>DB Test</title>
  </head>
  <body>

  <h2>Results</h2>

<table>
	<thead>
		<th>id</th>
		<th>name</th>
		<th>age</th>
	</thead>
	<tbody>
	<c:forEach var="row" items="${rs.rows}">
		<tr>
			<td>${row.id}</td>
			<td>${row.name}</td>
			<td>${row.age}</td>
		<tr>		
	</c:forEach>
	</tbody>
</table>

  </body>
</html>