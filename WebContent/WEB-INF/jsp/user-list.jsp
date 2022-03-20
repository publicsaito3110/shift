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

	<form action="UserListSearchServlet" method="post">
		<p>キーワード:<input type="text" name="keyWord" value="${keyWord}"></p>
		<c:if test="${afterFormFlag}">
			<span><a href="UserListServlet">条件を解除する</a></span>
		</c:if>
		<br>
		<input type="submit" value="検索する">
	</form>
	<br>

	<table border="1" cellspacing="0">
	<thead><tr>
		<th>ID</th>
		<th>ユーザー名</th>
		<th>カナ</th>
		<th>性別</th>
		<th>更新</th>
		</tr>
	</thead>

	<tbody>
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
				<td>
					<form action="UserInfoOneServlet" method="post">
						<button type="submit" name="id" value="${b.id}">更新</button>
					</form>
				</td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
	<br>
	<a href="UserSignUpServlet">新規登録</a>

</body>
<style>

</style>

</html>