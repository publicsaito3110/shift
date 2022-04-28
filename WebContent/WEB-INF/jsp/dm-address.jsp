<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/header.css">
</head>
<body>
	<jsp:include page="common/header.jsp" flush="true" />

	<div class="chat-msg-box">
		<div class="chat-title">
			<h1 class="chat-title-h1">メッセージ</h1><label class="open" for="pop-up">連絡先+</label>
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
	<div id="talk"></div>
	</div>

	<%--ポップアップ(連絡先一覧) --%>
	<input type="checkbox" id="pop-up">
	<div class="overlay">
		<div class="window">
			<div class="window-wrap">
				<label class="close" for="pop-up">閉じる</label>
				<h3 class="window-title">連絡先</h3>
				<div class="address-wrap">
					<ul class="address-list">
					<c:forEach var="i" items="${userList}">
						<li><button class="address-btn" value="${i.id}">${i.name}</button></li>
					</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>

$(function() {
	//チャットボタン押下時の処理
	$('.chat-address-btn').on('click', function (){

		$(".chat-address-btn").prop("disabled", false);
		$(this).prop("disabled", true);
		$.ajax({
			url: "DmTalkServlet",
			type: "POST",
			data: {receiveUser : $(this).val()}
		}).done(function (result) {
			// 通信成功時のコールバック
			$("#talk").html(result);
		}).fail(function () {
			// 通信失敗時のコールバック
			alert("読み込みに失敗しました。");
		}).always(function (result) {
        // 常に実行する処理
		});
	});

	//アドレスボタン押下時の処理
	$('.address-btn').on('click', function (){

		$(".address-btn").prop("disabled", false);
		$(this).prop("disabled", true);
		$("#pop-up").prop("checked", false);
		$.ajax({
			url: "DmTalkServlet",
			type: "POST",
			data: {receiveUser : $(this).val()}
		}).done(function (result) {
			// 通信成功時のコールバック
			$("#talk").html(result);
		}).fail(function () {
			// 通信失敗時のコールバック
			alert("読み込みに失敗しました。");
		}).always(function (result) {
        // 常に実行する処理
		});
	});

});
</script>
<style>
body {
	margin: auto;
}

html {
	overflow: hidden;
}

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
	top: 16px;
	height: 80%;
	padding: inherit;
	list-style: none;
	overflow: auto;
    overflow-x: hidden;
    margin: 0px;
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
	left: 31%;
	width: 69%;
	height: 98%;
}

#talk {
	position: absolute;
	width: 100%;
	height: 100%;
}

/*ポップアップ*/
.open {
    position: relative;
    top: 0%;
	background-color: #99cc65;
	color: white;
	padding: 10px;
}
#pop-up {
	display: none;
	position: relative;
    top: 4%;
    left: 82%;
}
.overlay {
	display: none;
}
#pop-up:checked + .overlay {
	display: block;
	position: fixed;
	width: 100%;
	height: 100vh;
	top: 0;
	left: 0;
	z-index: 9999;
	background: rgba(0, 0, 0, 0.6);
}
.window {
	position: fixed;
	top: 50%;
	left: 50%;
	width: 90vw;
	max-width: 360px;
	height: 300px;
	background-color: #fff;
	border-radius: 4px;
	align-items: center;
	transform: translate(-50%, -50%);
}
.window-wrap {
    position: absolute;
    width: 100%;
    height: 100%;
}
.close {
	position: absolute;
	top: 4px;
	right: 4px;
	cursor:pointer;
}
.window-title {
	text-align: center;
}
.address-list {
	list-style: none;
}
.address-wrap {
	overflow: auto;
	overflow-x: hidden;
    height: 76%;
}
.address-btn {
	width: 100%;
}
</style>
</html>