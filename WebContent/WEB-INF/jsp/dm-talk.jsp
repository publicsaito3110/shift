<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<body>
	<h2 class="chat-header">${msgToName}</h2>
	<div class="talk-list">
		<c:forEach var="i" items="${talkList}">
			<div class="${i.sendUser} chat-wrap">
				<div class="profile-icon-wrap">
					<div class="profile-icon"></div>
				</div>
				<div class="chatting">
					<span class="talk-text">${i.msg}</span>
				</div>
				<span class="send-date">${i.msgDate}</span>
			</div>
		</c:forEach>
	</div>

	<div class="send-form">
		<textarea id="msg-text" name="msg" cols="60" rows="10" maxlength="200"></textarea>
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
<style>

.chat-header {
	transform: scale(0.99,0.99);
	text-align: center;
	padding: 1rem 3rem;
    padding-top: 1rem;
    padding-right: 3rem;
    padding-bottom: 1rem;
    padding-left: 3rem;
    color: #fff;
    border-radius: 100vh;
    background-image: linear-gradient(to left, #a2c4cd 0%, #90cbcb 100%);
}

.talk-list {
	overflow: auto;
	overflow-x: hidden;
	width: 100%;
	height: 100%;
}

.chat-wrap {
display: flex;
flex-wrap: wrap;
}


.LOGIN_USER {
  flex-direction: row-reverse;
    position: relative;
    top: 1%;
    right: 2%;
}

.NOT_LOGIN_USER {
position: relative;
top: 1%;
left: 2%;
  flex-direction: row;
}

.chatting {
position: relative;
display: inline-block;
margin: 3px 20px;
padding: 10px 13px;
background: #ccffcc;
text-align: left;
border-radius: 12px;
}

/* 吹き出しの三角部分の作成 */
.chatting::after {
content: "";
border: 15px solid transparent;
border-top-color: #ccffcc;
position: absolute;
top: 10px;
}

.NOT_LOGIN_USER .chatting {
background: #dddddd;
}

.NOT_LOGIN_USER .chatting::after {
border-top-color: #dddddd;
}

.LOGIN_USER .chatting {
background: #ccffcc;
}

.LOGIN_USER .chatting::after {
border-top-color: #ccffcc;
}

.NOT_LOGIN_USER .chatting::after {
left: -15px;
}

.LOGIN_USER .chatting::after {
right: -15px;
}

.profile-icon {
  color: #000;
  position: relative;
  margin-left: 3px;
  margin-top: 11px;
  width: 14px;
  height: 6px;
  border-left: solid 1px currentColor;
  border-right: solid 1px currentColor;
  border-top: solid 1px currentColor;
  border-bottom: solid 1px transparent;
  border-radius: 6px 6px 0 0;
  transform: scale(2, 2);
  top: 15%;
}
.profile-icon:before {
  content: '';
  position: absolute;
  left: 2px;
  top: -10px;
  width: 8px;
  height: 8px;
  border-radius: 50%;
  border: solid 1px currentColor;
}

.send-date {
    display: flex;
    align-items: end;
    font-size: 70%;
}

.send-form {
	position: fixed;
    bottom: 0%
}

#msg-text {
	resize: none;
}

</style>