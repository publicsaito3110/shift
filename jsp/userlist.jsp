<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/header.css">
</head>
<body>
	<jsp:include page="header.jsp" flush="true" />

	<h1>ユーザー一覧</h1>

	<table border="1" cellspacing="0">
		<th>ID</th>
		<th>ユーザー名</th>
		<th>カナ</th>
		<th>性別</th>
		<th>更新</th>

		<c:forEach var="b" items="${dbList}" varStatus="status">
			<tr>
				<td>${b.id}</td>
				<td>${b.name}</td>
				<td>${b.nameKana}</td>
				<td>
					<c:choose>
						<c:when test="${b.gender == 1}">   <%--genderが 1:男  2:女 と表示する--%>
	    					男
	    				</c:when>
						<c:otherwise>
       						女
    					</c:otherwise>
					</c:choose>
				</td>
				<td>更新ボタン</td>
			</tr>
		</c:forEach>
	</table>
	<br>
	<a href="UserSighnUpServlet">新規登録</a>

</body>


</html>