<%@page contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import = "bean.ScheduleBean" %>

<!DOCTYPE HTML>

<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/header.css">
</head>
<body>
	<jsp:include page="common/header.jsp" flush="true" />


	<h1>ようこそ${name}さん</h1>

	<div>
		<table>
			<thead>
				<tr>
					<th class="title">お知らせ</th>
				</tr>
			</thead>
		</table>
	</div>

	<div>
	    <ul class="info-detail">
	    	<c:forEach var="b" items="${newsList}" varStatus="status">
	        <li>
	            <span class="date-text">${b.ymd}</span><span class="label">${b.labelNew}</span>
	            <a href="#" class="content-text">${b.content}</a>
	        </c:forEach>
	    </ul>
	</div>

	<form action="CalendarServlet" method="post">
		<input type="submit" value="スケジュール">
	</form>
	<form action="UserListServlet" method="post">
		<input type="submit" value="ユーザー">
	</form>
</body>
<style>
.title{
	border-left: solid 15px #333;
	background: #999;
	margin-bottom: 5px;
	padding: 0.5rem;
	width: 0.1%;
	color: #fff;
}
.info-detail li{
	list-style: none;
	padding: 1rem 1rem;
	border-bottom: solid #999 1px;
	position: relative;
}

.date-text{
	position: relative;
	right: 1%;
	bottom: 4px;
	font-size: 25px;
}

.label{
	position: absolute;
	left: 20%;
	background-color:#ffbf1f;
	border-radius:3px;
	color:#fff;
	display:inline-block;
	margin-right:20px;
	font-size: 18px;
}

.content-text{
	position: absolute;
	left: 30%;
	top: 11px;
	font-size: 25px;
	color: black;
}
</style>
</html>