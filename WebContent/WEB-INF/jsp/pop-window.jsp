<%@page contentType="text/html" pageEncoding="UTF-8" session="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<%--ポップアップウィンドウ formの処理後に自動的に表示 --%>
	<c:if test="${afterFormFlag}">
		<div class="modal_wrap">
			<input id="trigger" type="checkbox" checked="checked">
			<div class="modal_overlay">
				<label for="trigger" class="modal_trigger"></label>
				<div class="modal_content">
					<label for="trigger" class="close_button">✖️</label>
					<p class="modal_title2">${popTitle}</p>
					<c:choose>
						<c:when test="${result}">
							<p class="suc-text">${resultText}</p>
						</c:when>
						<c:otherwise>
							<p class="er-text">${resultText}</p>
							<span class="round_btn"></span>
							<p>もう一度、正しい値を入力してください。</p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</c:if>