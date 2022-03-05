<%@page contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import = "bean.ScheduleBean" %>

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
						<li><a href="<c:url value="/Calendar"><c:param name="parameter" value="${paramValue}" /></c:url>">ホーム</a></li>
						<li><a href="<c:url value="/developDoGet"><c:param name="parameter" value="${paramValue}" /></c:url>">シフトを修正する</a></li>
						<li><a href="#">メニューリスト3</a></li>
				</ul>
			</div>
			<div class="hamburger-demo-cover"></div>
		</div>

	</div>

	<div class="calendar-wrap_">

		<%-- カレンダー1行目 --%>

	    <h1>${year}年${month}月</h1>
	    <table border="1" cellspacing="0" class="calendar">
	      <tr>
	        <th class="sun">日</th>
	        <th>月</th>
	        <th>火</th>
	        <th>水</th>
	        <th>木</th>
	        <th>金</th>
	        <th class="sat">土</th>
	      </tr>

			<%--カレンダーの2行目から下 --%>
				<c:forEach var="b" items="${dayList}" varStatus="status">
					<c:if test="${status.count % 7 == 1}">
						<tr>
					</c:if>

					<td>

						<%--カレンダーの日付に (土)->青, (日)->赤 をつける --%>
						<c:choose>
	    					<c:when test="${status.count % 7 == 1}">
	    						<h3 class="sun">${b.day}</h3>
	    					</c:when>
							<c:when test="${status.count % 7 == 0}">
	    						<h3 class="sat">${b.day}</h3>
	    					</c:when>
	    					<c:otherwise>
       							<h3>${b.day}</h3>
    						</c:otherwise>
	    				</c:choose>

						<%--日付の下にDAOから受け取った戻り値をそれぞれ表示させる --%>
						<c:if test="${b.memo1 != null}">
							<div class="memo" style="background-color: yellow;">(午前)${b.memo1}</div>
						</c:if>
						<br>
						<c:if test="${b.memo2 != null}">
							<div class="memo" style="background-color: yellow;">(午後)${b.memo2}</div>
						</c:if>
						<br>
						<c:if test="${b.memo3 != null}">
							<div class="memo" style="background-color: yellow;">(夜間)${b.memo3}</div>
						</c:if>
					</td>


					<c:if test="${status.count % 7 == 0}">
						</tr>
					</c:if>
				</c:forEach>
		    </table>
	</div>



<style>
* {
	margin: 0;
	padding: 0;
}

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


.calendar-wrap {
	width: 500px;
	background: #eee;
	color: #333;
}

h1 {
	line-height: 100px;
	text-align: center;
	font-size: 30px;
}

.calendar {
	padding: 0px;
	width: 100%;
	table-layout: fixed;
	border: ssssss3px;
	border-color: #CCC;
}

th, td {
	padding-bottom: 50px;
	background-color: #fff;
}

th {
	font-size: 25px;
	border: none;
	padding: 10px 0px 5px 0px;
	height: 37px;
	vertical-align: baseline;
	background-color: #f6efdb;
}

td {
	padding: 0px 0px 5px 5px;
	height: 110px;
	font-weight: bold;
	vertical-align: top;
}

.memo{
	text-align: left;
}

.sun {
	color: orangered;
}

.sat {
	color: #0000b6;
}
</style>


</body>
</html>