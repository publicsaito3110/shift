<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<body>
	<ul class="info-detail">
		<c:forEach var="i" items="${recordNewsList}" varStatus="status">
			<li class="news-list-btn">
				<span class="date-text">${i.ymd}</span>
				<a class="content-text">${i.content}</a>
				<input type="hidden" value="${i.content}">
			</li>
		</c:forEach>
	</ul>
</body>
</html>