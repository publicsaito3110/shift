<%@page contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import = "bean.ScheduleBean" %>

<!DOCTYPE HTML>

<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/header.css">
</head>
<body>
	<jsp:include page="header.jsp" flush="true" />


	<h1>ようこそ${name}さん</h1>

	<form action="CalendarServlet" method="post">
		<input type="submit" value="スケジュール">
	</form>
	<form action="UserListServlet" method="post">
		<input type="submit" value="ユーザー">
	</form>
</body>
</html>