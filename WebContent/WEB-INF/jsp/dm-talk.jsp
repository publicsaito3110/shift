<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/dm-talk.css">
</head>
<body>
	<h2 class="chat-header">${msgToName}</h2>
	<div class="talk-list">
		<c:forEach var="i" items="${talkList}">
			<div class="${i.sendUser} chat-wrap">
				<div class="profile-icon-wrap">
					<div class="profile-icon"></div>
				</div>
				<div class="chatting">
					<span class="talk-text">${i.msgAfterBreakLine}</span>
				</div>
				<span class="send-date">${i.msgDateFormatTlakDate}</span>
			</div>
		</c:forEach>
		<br>
		<br>
	</div>

	<div class="send-form">
		<textarea id="msg-text" name="msg" cols="50" maxlength="200"></textarea>
		<button type="submit" id="msg-send-btn" value="${msgToId}" disabled>送信</button>
		</div>
</body>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(function(){
	//メッセージテキスト入力時の処理
	  $('#msg-text')
	  .on('input', function(){
		  if($(this).val()){
			  $("#msg-send-btn").prop('disabled', false);
		  }else{
			  $("#msg-send-btn").prop('disabled', true);
		  }

	    if ($(this).outerHeight() > this.scrollHeight){
	      $(this).height(1)
	    }
	    while ($(this).outerHeight() < this.scrollHeight){
	      $(this).height($(this).height() + 1)
	    }
	  });

		// 送信ボタン押下時の処理
		$('#msg-send-btn').on('click', function (){
			$.ajax({
			url: "DmSendServlet",
			type: "POST",
			data: {msg : $("#msg-text").val(), receiveUser : $("#msg-send-btn").val()}
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