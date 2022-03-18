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

	<h1>ユーザー登録</h1>

	<form action="UserSignUpServlet" method="post">
		<table border="1" cellspacing="0">
			<tr>
				<th><span>ID</span> <span class="text-red">※</span></th>
				<td>
					<input type="text" name="id" value="${id}" placeholder="A123"><span>${erId}</span>
				</td>
			</tr>
			<tr>
				<th><span>名前</span> <span class="text-red">※</span></th>
				<td>
					<input type="text" name="name" value="${name}" placeholder="田中太郎"><span>${erName}</span>
				</td>
			</tr>
			<tr>
				<th>フリガナ(任意)</th>
				<td>
					<input type="text" name="nameKana" value="${nameKana}" placeholder="タナカタロウ"><span>${erNameKana}</span>
				</td>
			</tr>
			<tr>
				<th>性別</th>
				<td>
					<input type="radio" name="gender" value="1" checked>男
					<input type="radio" name="gender" value="2">女
					<span>${erGender}</span>
				</td>
			</tr>
			<tr>
				<th>パスワード</th>
				<td>
					<input type="password" name="password" value="${password}"><span>${erPassword}</span>
				</td>
			</tr>
			<tr>
				<th>住所</th>
				<td>
					<input type="text" name="address" value="${address}" placeholder="東京都新宿区○○町1-1"><span>${erAddress}</span>
				</td>
			</tr>
			<tr>
				<th>TEL</th>
				<td>
					<input type="tel" name="tel" value="${tel}" placeholder="01234567890"><span>${erTel}</span>
				</td>
			</tr>
			<tr>
				<th>メール</th>
				<td>
					<input type="email" name="email" value="${email}"><span>${erEmail}</span>
				</td>
			</tr>
			<tr>
				<th>備考</th>
				<td>
					<input type="text" name="note" value="${note}"><span>${erNote}</span>
				</td>
			</tr>
		</table>
		<br>
		<input type="submit" value="登録">
	</form>
	<br>
	<a href="UserListServlet">ユーザー一覧へ</a>


	<%--ポップアップウィンドウ  Form送信後に自動的に表示 --%>
	<c:if test="${afterFormFlag}">
		<div class="modal_wrap">
			<input id="trigger" type="checkbox" checked="checked">
			<div class="modal_overlay">
				<label for="trigger" class="modal_trigger"></label>
				<div class="modal_content">
					<label for="trigger" class="close_button">✖️</label>
					<p class="modal_title2">ユーザー新規登録結果</p>
					<%--resultの結果によって表示内容を分岐 --%>
					<c:choose>
						<c:when test="${sighnUpResult}">
							<p class="suc-text">${resulText}</p>
						</c:when>
						<c:otherwise>
							<p class="er-text">${resulText}</p>
							<span class="round_btn"></span>
							<p>もう一度、正しい値を入力してください</p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</c:if>

</body>


</html>