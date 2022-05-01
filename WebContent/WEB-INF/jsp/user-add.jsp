<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/header.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/mordal.css">
</head>
<body>
	<jsp:include page="common/header.jsp" flush="true" />

	<h1>ユーザー登録</h1>

	<form action="UserAddServlet" method="post">
		<table border="1">
			<tr>
				<th><span>ID</span> <span class="text-red">※</span></th>
				<td>
					<input type="text" class="text-form" name="id" value="${id}" placeholder="A123"><span class="text-red">${valiBean.erId}</span>
				</td>
			</tr>
			<tr>
				<th><span>名前</span> <span class="text-red">※</span></th>
				<td>
					<input type="text" class="text-form" name="name" value="${name}" placeholder="田中太郎"><span class="text-red">${valiBean.erName}</span>
				</td>
			</tr>
			<tr>
				<th>フリガナ(任意)</th>
				<td>
					<input type="text" class="text-form" name="nameKana" value="${nameKana}" placeholder="タナカタロウ"><span class="text-red">${valiBean.erNameKana}</span>
				</td>
			</tr>
			<tr>
				<th>性別</th>
				<td>
					<input type="radio" name="gender" value="1" checked>男
					<input type="radio" name="gender" value="2" ${checkGender2}>女
					<span class="text-red">${valiBean.erGender}</span>
				</td>
			</tr>
			<tr>
				<th>パスワード</th>
				<td>
					<input type="password" class="text-form" name="password" value="${password}"><span class="text-red">${valiBean.erPassword}</span>
				</td>
			</tr>
			<tr>
				<th>住所</th>
				<td>
					<input type="text" class="text-form" name="address" value="${address}" placeholder="東京都新宿区○○町1-1"><span class="text-red">${valiBean.erAddress}</span>
				</td>
			</tr>
			<tr>
				<th>TEL</th>
				<td>
					<input type="tel" name="tel" class="text-form" value="${tel}" placeholder="01234567890"><span class="text-red">${valiBean.erTel}</span>
				</td>
			</tr>
			<tr>
				<th>メール</th>
				<td>
					<input type="email" class="text-form" name="email" value="${email}"><span class="text-red">${valiBean.erEmail}</span>
				</td>
			</tr>
			<tr>
				<th>管理者</th>
				<td>
					役職1<input type="checkbox" name="adminFlag" value="1" ${checkAdminFlag}><span class="text-red">${valiBean.erAdminFlag}</span>
				</td>
			</tr>
			<tr>
				<th>備考</th>
				<td>
					<textarea class="textarea-form" name="note" rows="" cols="">${note}</textarea><span class="text-red">${valiBean.erNote}</span>
				</td>
			</tr>
		</table>
		<br>
		<input class="add-btn" type="submit" value="登録">
	</form>
	<br>
	<a href="UserListServlet">ユーザー一覧へ</a>


	<%--ポップアップウィンドウ  Form送信後に自動的に表示 --%>
	<jsp:include page="common/pop-window.jsp" flush="true" />

</body>
<style>
body {
	margin: auto;
}

table {
	width: 100%;
	border: none;
}

th {
	width: 15%;
}

td {
	background-color: #f1ecec;
}
.text-form {
	transform: scale(1,1.5);
    border: none;
}
.text-form:focus {
  outline:none;
}

.textarea-form {
	width: -webkit-fill-available;
    height: 95px;
}

.add-btn {
position: absolute;
    right: 40%;
    left: 40%;
    margin: auto;
    border: none;
    height: 6%;
    font-size: 21px;
    color: white;
    background-color: #97cdf3;
}

.add-btn:hover {
	background-color: #7dc6f9;
}
</style>

</html>