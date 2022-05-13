<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/common/header.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/dm-address.css">
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
</html>