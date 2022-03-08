<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/header.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/calendar.css">
</head>
<body>
	<jsp:include page="header.jsp" flush="true" />

	<h1>ユーザー一覧</h1>

	<table>
		<tr>
			<th>ID</th>
		</tr>
		<tr>
			<th>ユーザー名</th>
		</tr>
		<tr>
			<th>カナ</th>
		</tr>
		<tr>
			<th>性別</th>
		</tr>
		<tr>
			<th>更新</th>
		</tr>

		<tr>
			<td></td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td></td>
		</tr>
		<tr>
			<td></td>
		</tr>
	</table>



</body>


</html>