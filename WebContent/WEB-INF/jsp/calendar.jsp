<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/header.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/calendar.css">
</head>
<body>
	<jsp:include page="common/header.jsp" flush="true" />

	<div class="parents">
		<div class="before-month"><a class="month-btn" href="CalendarServlet?ym=${beforeYm}">前の月へ</a></div>
		<div class="after-month"><a class="month-btn" href="CalendarServlet?ym=${afterYm}">次の月へ</a></div>

		<div>
		    <h1>${year}年${month}月</h1>
		    <table class="calendar-table" border="1" cellspacing="0" class="calendar">
		      <tr>
		        <th class="sun">日</th>
		        <th>月</th>
		        <th>火</th>
		        <th>水</th>
		        <th>木</th>
		        <th>金</th>
		        <th class="sat">土</th>
		      </tr>
				<%--カレンダーの2行目から下 --%>
				<c:forEach var="b" items="${dayList}" varStatus="status">
					<c:if test="${status.count % 7 == 1}">
						<tr>
					</c:if>
					<td>
						<a style="width:100%; height:100%; display:block;" href="ScheduleDayServlet?year=${year}&month=${month}&day=${b.day}">

						<%--カレンダーの日付に (土)->青, (日)->赤 (平日)->黒 をつける --%>
						<c:choose>
	    					<c:when test="${status.count % 7 == 1}">
	    						<h3 class="sun">${b.day}</h3>
	    					</c:when>
							<c:when test="${status.count % 7 == 0}">
	    						<h3 class="sat">${b.day}</h3>
	    					</c:when>
	    					<c:otherwise>
	      							<h3 class="text-balack">${b.day}</h3>
	   						</c:otherwise>
	    				</c:choose>

						<%--日付の下にDAOから受け取った戻り値をそれぞれ表示させる --%>
						<c:if test="${b.user1 != null || b.memo1 != null}">
							<div class="memo1 text-balack" style="background-color: yellow;">(午前)${b.user1} ${b.memo1}</div>
						</c:if>
						<c:if test="${b.user2 != null || b.memo2 != null}">
							<div class="memo2 text-balack" style="background-color: yellow;">(午後)${b.user2} ${b.memo2}</div>
						</c:if>
						<c:if test="${b.user3 != null || b.memo3 != null}">
							<div class="memo3 text-balack" style="background-color: yellow;">(夜間)${b.user3} ${b.memo3}</div>
						</c:if>

						</a>
					</td>
					<c:if test="${status.count % 7 == 0}">
						</tr>
					</c:if>
				</c:forEach>
		    </table>
		</div>
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
}
.calendar-table{
	position: relative;
	bottom: 7%;
	width: 100%;
}

.before-month{
	position: relative;
	top: 16%;
	left: 2%;
}

.after-month{
	position: relative;
	left: 88%;
	top: 9%;
}

.month-btn{
	display: inline-block;
	max-width: 180px;
	text-align: left;
	background-color: #96d381;
	font-size: 16px;
	color: #FFF;
	text-decoration: none;
	font-weight: bold;
	padding: 8px 17px 6px 17px;
	border-radius: 4px;
}

.month-btn:hover {
  opacity: 0.8;
}

.calendar-table {
	position: relative;
	bottom: 7%;
}
</style>
</html>

