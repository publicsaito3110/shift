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
		<div class="chat-msg-box">
			<div class="chat-title">
				<h1 class="chat-title-h1">メッセージ</h1>
			</div>
			<ul class="chat-list">
				<c:forEach var="i" items="${msgList}" varStatus="status">
				<li>
					<button class="chat-address-btn" formaction="" formmethod="post" value="${i.msgToId}">
						<span class="icon">〇${i.msgToName}</span>
						<p>${i.msg}</p>
					</button>
				</li>
				</c:forEach>
			</ul>
		</div>
		<div class="talk-box">

		</div>
	</div>
</body>
<style>

.chat-msg-box {
	position: absolute;
	width: 50%;
	height: 98%;
	 border-right: 1px solid gray;
}

.chat-title {
	position: relative;
	bottom: 2%;
}

.chat-title-h1 {
	padding: 0.6em;
	background: #e0edff;
	transform: scale(0.7);
	float: left;
}

.chat-title-h1:after {
	position: absolute;
	content: '';
	top: 100%;
	left: 30px;
	border: 15px solid transparent;
	border-top: 15px solid #e0edff;
	width: 0;
	height: 0;
}

.talk-box {
	position: absolute;
	left: 50%;
	width: 50%;
	height: 98%;
}

.chat-address-btn {
	width: 100%;
}

</style>
</html>