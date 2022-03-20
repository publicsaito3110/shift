<%@page contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>

<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/header.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/mordal.css">
</head>
<body>

	<jsp:include page="header.jsp" flush="true" />

	<h1>シフト変更</h1>

	<h3>${year}年${month}月${day}日(修正前)</h3>

	<table border="1" cellspacing="0" class="tb">
		<tr>
			<th>時間</th>
			<th>当日のシフト</th>
		</tr>

		<tr>
			<td>(午前)</td>
			<td>${memo1}</td>
		</tr>
		<tr>
			<td>(午後)</td>
			<td>${memo2}</td>
		</tr>
		<tr>
			<td>(夜間)</td>
			<td>${memo3}</td>
		</tr>
	</table>


	<h3>${year}年${month}月${day}日 (修正後)</h3>

	<form method="post">
		<table border="1" cellspacing="0" class="tb">
			<tr>
				<th>時間</th>
				<th>修正後のシフト</th>
			</tr>

			<tr>
			<td>(午前)</td>
			<td><input type="text" class="tx" name="afterMemo1" value="${memo1}"></td>
			</tr>
			<tr>
				<td>(午後)</td>
				<td><input type="text" class="tx" name="afterMemo2" value="${memo2}"></td>
			</tr>
			<tr>
				<td>(夜間)</td>
				<td><input type="text" class="tx" name="afterMemo3" value="${memo3}"></td>
			</tr>
		</table>

		<c:choose>
			 <%--memo1,memo2,memo3のいずれかに値が入っているとき修正ボタンと削除ボタンを追加させる --%>
			<c:when test="${memo1 != null || memo2 != null || memo3 != null}">
				<button type="submit" formaction="ScheduleCorrectServlet" name="sqlType" value="UPDATE">修正する</button>
				<button type="submit" formaction="ScheduleCorrectServlet" name="sqlType" value="DELETE">シフトを全て削除する</button>
			</c:when>
			<c:otherwise>
				 <%--memo1,memo2,memo3が全てnullとき登録ボタンを追加させる --%>
				<button type="submit" formaction="ScheduleCorrectServlet" name="sqlType" value="INSERT">登録する</button>
			</c:otherwise>
		</c:choose>

		<%--日付の受け渡し用 --%>
		<input type="hidden" name="year" value="${year}">
		<input type="hidden" name="month" value="${month}">
		<input type="hidden" name="day" value="${day}">
		<input type="hidden" name="beforeMemo1" value="${memo1}">
		<input type="hidden" name="beforeMemo2" value="${memo2}">
		<input type="hidden" name="beforeMemo3" value="${memo3}">

	</form>
	<br>
	<a href="CalendarServlet">ホームへ戻る</a>


	<%--ポップアップウィンドウ  Form送信後に自動的に表示 --%>
	<jsp:include page="pop-window.jsp" flush="true" />

</body>
</html>