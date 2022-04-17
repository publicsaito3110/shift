<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<body>

	<c:forEach var="i" items="${talkList}">
		<div class="${i.sendUser} chat-wrap">
			<div class="profile-icon-wrap">
				<div class="profile-icon"></div>
			</div>
			<div class="chatting">
				<span class="talk-text">${i.msg}</span>
			</div>
		</div>
	</c:forEach>

	<div class="send-form">
		<form action="DmSendServlet" method="post">
			<textarea class="msg-text" name="msg"></textarea> <input type="submit" class="msg-send-btn" value="送信">
		</form>
	</div>
</body>
<style>


.chat-wrap {
display: flex;
flex-wrap: wrap;
}


.LOGIN_USER {
  flex-direction: row-reverse;
}

.NOT_LOGIN_USER {
position: relative;
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

</style>