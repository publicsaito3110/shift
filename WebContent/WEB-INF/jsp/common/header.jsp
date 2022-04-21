<%@page contentType="text/html" pageEncoding="UTF-8" session="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="demobox-header">
	<span class="demobox-sitename">SiteName</span>
	<%--ハンバーガーメニュー --%>
	<div class="hamburger-demo-menubox">
		<input id="hamburger-demo4" type="checkbox" class="input-hidden">
		<label for="hamburger-demo4" class="hamburger-demo-switch hamburger-demo-switch4">
		 <span class="hamburger-switch-circle"></span>
		</label>
		<div class="hamburger-demo-menuwrap hamburger-menuwrap-right">
			<ul class="hamburger-demo-menulist hamburger-menulist-circle">
				<li><a href="HomeServlet">ホーム</a></li>
				<li><a href="CalendarServlet">今月のシフト</a></li>
				<li><a href="DmAddressServlet">メッセージ</a></li>
				<li><a href="UserListServlet">ユーザー一覧</a></li>
				<c:if test="${isAdministrator}">
					<li><a href="NewsEditServlet">お知らせ編集</a></li>
				</c:if>
				<li><a href="LogoutServlet">ログアウト</a></li>
			</ul>
		</div>
		<div class="hamburger-demo-cover"></div>
	</div>

</div>