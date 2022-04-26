<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/header.css">
</head>
<body>
	<jsp:include page="common/header.jsp" flush="true" />

	<div class="parents">
		<h1>ユーザー一覧</h1>

		<div class="search-user">
			<c:if test="${isAdministrater}">
				<form action="UserListServlet" method="post">
					<p>キーワード:<input type="text" name="keyWord" value="${keyWord}"><input type="submit" value="検索"></p>
					<c:if test="${afterFormFlag}">
						<span><a href="UserListServlet">条件を解除する</a></span>
					</c:if>
				</form>
			</c:if>
		</div>
		<br>
		<c:if test="${isAdministrater}">
			<a href="UserAddServlet" class="signup-btn"><span>新規登録</span></a>
		</c:if>

		<div class="page-nation">
			<c:forEach var="i" items="${pageList}" varStatus="status">
				<form name="pushPage" method="post" action="UserListServlet">
					<input type="hidden" name="page" value="${i}">
					<input type="hidden" name=keyWord value="${keyWord}">
					<a class="pages" href="javascript:document.pushPage[${status.count -1}].submit()">${i}</a>
				</form>
			</c:forEach>
		</div>

		<table class="user-list" border="1" cellspacing="0">
		<thead><tr>
			<th>ID</th>
			<th>ユーザー名</th>
			<th>カナ</th>
			<th>性別</th>
			<th>更新</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="b" items="${userList}" varStatus="status">
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
						<form action="UserOneServlet" method="post">
							<button class="upd-btn" type="submit" name="id" value="${b.id}" ${inputDisabled}>更新</button>
						</form>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
	</div>

</body>
<style>
body {
	margin: auto;
}

.parents{
	position: absolute;
	width: 100%;
	height: 85%;
	overflow-x: hidden;
}
h1{
	position: relative;
	   left: 5%;
}

.search-user{
	position: relative;
	left: 30%;
   }

.user-list{
	position: relative;
	bottom: 2%;
	margin: auto;
	table-layout: fixed;
	width: 100%;
}

td{
	text-align: center;
}

.upd-btn{
	width: 100%;
}

.signup-btn{
	position: relative;
	top: 48%;
	left: 13%;
	font-weight: bold;
	padding: 10px 12px 15px 12px;
	text-decoration: none;
	color: darkslategray;
	background: #b3d0d7;
}

.signup-btn > span {
  border-bottom: solid 2px darkslategray;
}

.page-nation{
	position: relative;
	top: 63%;
	left: 13%;
	display:inline-flex;
}

.pages{
	display: block;
	text-decoration: none;
	color: #333;
	padding: 20px;
}

.pages:hover{
    background-color: #333;
    color: aliceblue;
}

</style>

</html>