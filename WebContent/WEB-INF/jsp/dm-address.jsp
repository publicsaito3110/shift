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
				<c:forEach var="i" items="${chatList}" varStatus="status">
				<li>
					<button class="chat-address-btn" name="receiveUser" value="${i.msgToId}">
						<span class="icon">〇${i.msgToName}</span>
						<p>${i.msg}</p>
					</button>
				</li>
				</c:forEach>
			</ul>
		</div>
		<div class="talk-box">
			<c:forEach var="i" items="${talkList}">
				<p>${i.msg}</p>
			</c:forEach>
		</div>
	</div>



<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>

$(function() {
	// ボタン押下時の処理
	$('.chat-address-btn').on('click', function (){
		$.ajax({
		url: "DmTalkServlet",
		type: "POST",
		data: {receiveUser : $(this).val()}
		}).done(function (result) {
			// 通信成功時のコールバック
			alert("読み込み成功");
			$("${talkList}").load(result);
		}).fail(function () {
			// 通信失敗時のコールバック
			alert("読み込みに失敗しました。");
		}).always(function (result) {
        // 常に実行する処理
		});
	});
});
</script>
</body>
<style>

.chat-msg-box {
	position: absolute;
	width: 30%;
	height: 98%;
	 border-right: 1px solid gray;
}

.chat-title {
	position: relative;
	width: 100%;
	height: 15%;
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

.chat-list {
	position: relative;
	height: 80%;
	bottom: 0%;
	padding: inherit;
	list-style: none;
}

.chat-list li:first-child {
	border-top: 1px solid gray;
}

.chat-list li {
	border-bottom: 1px solid gray;
}

.chat-address-btn {
	width: 100%;
	border: none;
	background-color: white;
}

.talk-box {
	position: absolute;
	left: 36%;
	width: 64%;
	height: 98%;
}

</style>
</html>