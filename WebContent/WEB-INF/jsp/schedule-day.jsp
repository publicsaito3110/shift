<%@page contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>

<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/common/header.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/common/mordal.css">
</head>
<body>

	<jsp:include page="common/header.jsp" flush="true" />

	<h1>シフト変更</h1>


	<h3>${year}年${month}月${day}日</h3>

	<form action="ScheduleDayModifyServlet" method="post">
		<table border="1" cellspacing="0" class="tb">
			<tr>
				<th>時間</th>
				<th>修正後のシフト</th>
			</tr>
			<tr>
			<td>(午前)</td>
			<td>
				<select name="user1">
					<c:forEach var="b" items="${scheduleDayList}" varStatus="status">
						<option value="${b.id}" ${b.checkUser1}>${b.user}</option>
					</c:forEach>
				</select>
				<input type="text" class="tx" name="memo1" value="${memo1}">
			</td>
			</tr>
			<tr>
				<td>(午後)</td>
				<td>
					<select name="user2">
					<c:forEach var="b" items="${scheduleDayList}" varStatus="status">
						<option value="${b.id}" ${b.checkUser2}>${b.user}</option>
					</c:forEach>
					</select>
					<input type="text" class="tx" name="memo2" value="${memo2}">
				</td>
			</tr>
			<tr>
				<td>(夜間)</td>
				<td>
					<select name="user3">
					<c:forEach var="b" items="${scheduleDayList}" varStatus="status">
						<option value="${b.id}" ${b.checkUser3}>${b.user}</option>
					</c:forEach>
					</select>
					<input type="text" class="tx" name="memo3" value="${memo3}">
				</td>
			</tr>
		</table>


		<button type="submit" name="sqlType" value="${btnValue1}">${btn1}</button>
		<c:if test="${sqlTypeUpdate}">
			<button type="submit" name="sqlType" value="${btnValue2}">${btn2}</button>
		</c:if>

		<%--日付の受け渡し用 --%>
		<input type="hidden" name="year" value="${year}">
		<input type="hidden" name="month" value="${month}">
		<input type="hidden" name="day" value="${day}">


	</form>
	<br>
	<a href="CalendarServlet">ホームへ戻る</a>


	<%--ポップアップウィンドウ  Form送信後に自動的に表示 --%>
	<jsp:include page="common/pop-window.jsp" flush="true" />

</body>
<style>
body {
	margin: auto;
}
</style>
</html>