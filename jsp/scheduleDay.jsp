<%@page contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>

<html>
<body>

		<div class="demobox-header">
		<span class="demobox-sitename">SiteName</span>
		<%--ハンバーガーメニュー --%>
		<div class="hamburger-demo-menubox">
			<input id="hamburger-demo4" type="checkbox" class="input-hidden">
			<label for="hamburger-demo4"
				class="hamburger-demo-switch hamburger-demo-switch4"> <span
				class="hamburger-switch-circle"></span>
			</label>
			<div class="hamburger-demo-menuwrap hamburger-menuwrap-right">
				<ul class="hamburger-demo-menulist hamburger-menulist-circle">
						<li><a href="<c:url value="/CalendarServlet"></c:url>">ホーム</a></li>
						<li><a href="#">メニューリスト2</a></li>
						<li><a href="#">メニューリスト3</a></li>
				</ul>
			</div>
			<div class="hamburger-demo-cover"></div>
		</div>

	</div>

	<h1>シフト変更</h1>

	<h3>${year}年${month}月${day}日 (修正前)</h3>

	<table border="1" cellspacing="0" class="tb">
		<tr>
			<th>時間</th>
			<th>当日のシフト</th>
		</tr>

		<tr>
		<td>(午前)</td>
		<td>${memo1}</td>
		</tr>
		<tr>
			<td>(午後)</td>
			<td>${memo2}</td>
		</tr>
		<tr>
			<td>(夜間)</td>
			<td>${memo3}</td>
		</tr>
	</table>


	<h3>${year}年${month}月${day}日 (修正後)</h3>
	<h4>*修正したい箇所のみ入力してください</h4>
	<p>(例)[修正前]Aさん, 未入力 → [修正後]Aさん, Bさん としたいとき</p>
	<p>Aさん(修正前) → 未入力(修正後), 未入力(修正前) → Bさん(修正後)</p>

	<form action="ModifyScheduleServlet" method="post">
		<table border="1" cellspacing="0" class="tb">
			<tr>
				<th>時間</th>
				<th>修正後のシフト</th>
			</tr>

			<tr>
			<td>(午前)</td>
			<td><input type="text" class="tx" name="afterMemo1" placeholder="${memo1}"></td>
			</tr>
			<tr>
				<td>(午後)</td>
				<td><input type="text" class="tx" name="afterMemo2" placeholder="${memo2}"></td>
			</tr>
			<tr>
				<td>(夜間)</td>
				<td><input type="text" class="tx" name="afterMemo3" placeholder="${memo3}"></td>
			</tr>
		</table>

		<%--修正前のスケジュール受け渡し用 --%>
		<input type="hidden" name="year" value="${year}">
		<input type="hidden" name="month" value="${month}">
		<input type="hidden" name="day" value="${day}">

		<input type="hidden" name="beforeMemo1" value="${memo1}">
		<input type="hidden" name="beforeMemo2" value="${memo2}">
		<input type="hidden" name="beforeMemo3" value="${memo3}">


		<button type="submit" name="btn" value="">修正する</button>

		<%--memo1,memo2,memo3のいずれかに値が入っているとき削除ボタンを追加させる --%>
		<c:if test="${memo1 != null || memo2 != null || memo3 != null}">
			<button type="submit" name="btn" value="delete" formmethod="post">シフトを削除する</button>
		</c:if>

	</form>


<a href="CalendarServlet">戻る</a>

<style>

.demobox-header {
	background: #ddd;
	height: 64px;
	padding: 1em;
}

.demobox-sitename {
	font-weight: 700;
	font-size: 18px;
}
/* 全体調整CSS */
.hamburger-demo-menubox * {
	font-size: 16px;
}

.hamburger-demo-menubox li {
	font-size: 14px;
}
/* hamburgerここから */
/* input非表示 */
.input-hidden {
	display: none;
}
/* label */
.hamburger-demo-switch {
	cursor: pointer;
	position: absolute;
	right: 3%;
	top: 0;
	z-index: 9999;
	width: 4em;
	height: 4em;
}
/* メニュー展開時にハンバーガーアイコンを固定 */
#hamburger-demo4:checked ~ .hamburger-demo-switch {
	position: fixed;
}
/* 円を用いたハンバーガーデザイン */
/* 外側の円 */
.hamburger-demo-switch4:before {
	content: "";
	position: absolute;
	width: 3em;
	height: 3em;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	border: 2px solid #333; /* 外側の円（枠線）の色 */
	border-radius: 50%;
}
/* 真ん中の円 */
.hamburger-switch-circle {
	height: 3px;
	width: 25px;
	background: #333; /* 真ん中の線（円）の色 */
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	transition: .3s;
}
/* 上下の線 */
.hamburger-switch-circle:before, .hamburger-switch-circle:after {
	content: "";
	position: absolute;
	width: 25px;
	top: 50%;
	left: 50%;
	transition: .3s;
}

.hamburger-switch-circle:before {
	border-top: 3px solid #333; /* ハンバーガーアイコン上側の線の色 */
	transform: translate(-50%, -300%);
}

.hamburger-switch-circle:after {
	border-bottom: 3px solid #333; /* ハンバーガーアイコン下側の線の色 */
	transform: translate(-50%, 200%);
}
/* アイコン･アニメーション */
#hamburger-demo4:checked ~ .hamburger-demo-switch .hamburger-switch-circle
	{
	height: 25px;
	border-radius: 50%;
}

#hamburger-demo4:checked ~ .hamburger-demo-switch .hamburger-switch-circle:before
	{
	width: 0;
}

#hamburger-demo4:checked ~ .hamburger-demo-switch .hamburger-switch-circle:after
	{
	width: 0;
}
/* 真ん中の×印 */
.hamburger-demo-switch4:after {
	content: "×";
	font-size: 0px;
	position: absolute;
	color: #fff; /* ハンバーガーの"×"マークの色 */
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	transition: .2s;
}

#hamburger-demo4:checked ~ .hamburger-demo-switch4:after {
	font-size: 25px;
}
/* メニューエリア */
.hamburger-demo-menuwrap {
	position: fixed;
	height: 100%;
	background: #fafafa; /* メニューエリアの背景色 */
	padding: 5em 3% 2em;
	z-index: 9998;
	transition: .3s;
	overflow-y: scroll; /* メニュー内容が多い場合に縦スクロール */
	top: 0;
	left: 100%;
	width: 70%;
}
/* メニューリスト */
.hamburger-demo-menulist {
	margin-right: 3%;
	padding-left: 5% !important; /* !important不要な場合あり */
	list-style: none;
}

.hamburger-demo-menulist li a {
	text-decoration: none;
	color: #333; /* メニューエリアの文字色 */
	display: block;
}
/* 円を用いたメニューリスト */
.hamburger-menulist-circle li {
	border: 1px solid;
	margin-bottom: 5px;
	border-radius: 2em;
}

.hamburger-menulist-circle li a {
	padding: .5em 2.5em .5em 1em;
	position: relative;
}

.hamburger-menulist-circle a:before {
	content: "";
	position: absolute;
	width: 1.5em;
	height: 1.5em;
	background: #333; /* メニューリスト矢印背景（円）の色 */
	border-radius: 50%;
	top: 50%;
	right: .5em;
	transform: translate(0, -50%);
}

.hamburger-menulist-circle a:after {
	content: "";
	position: absolute;
	width: 10px;
	height: 10px;
	border: 2.5px solid;
	border-color: #fff #fff transparent transparent;
	/* メニューリスト矢印の色（#fffのみ変更） */
	top: 50%;
	right: .5em;
	transform: translate(-80%, -50%) rotate(45deg);
}
/* メニューエリア･アニメーション */
/* 右から */
#hamburger-demo4:checked ~ .hamburger-demo-menuwrap {
	left: 30%;
}
/* コンテンツカバー */
#hamburger-demo4:checked ~ .hamburger-demo-cover {
	position: fixed;
	width: 100%;
	height: 100%;
	top: 0;
	left: 0;
	z-index: 9997;
	background: rgba(3, 3, 3, .5);
	display: block;
}

</style>
</body>
</html>