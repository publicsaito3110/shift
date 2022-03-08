<%@page contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>

<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/header.css">
</head>
<body>

	<jsp:include page="header.jsp" flush="true" />

	<h1>シフト変更</h1>

	<h3>${year}年${month}月${day}日(修正前)</h3>

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

	<form method="post">
		<table border="1" cellspacing="0" class="tb">
			<tr>
				<th>時間</th>
				<th>修正後のシフト</th>
			</tr>

			<tr>
			<td>(午前)</td>
			<td><input type="text" class="tx" name="afterMemo1" value="${memo1}"></td>
			</tr>
			<tr>
				<td>(午後)</td>
				<td><input type="text" class="tx" name="afterMemo2" value="${memo2}"></td>
			</tr>
			<tr>
				<td>(夜間)</td>
				<td><input type="text" class="tx" name="afterMemo3" value="${memo3}"></td>
			</tr>
		</table>

		<%--修正前のスケジュール受け渡し用 --%>
		<input type="hidden" name="year" value="${year}">
		<input type="hidden" name="month" value="${month}">
		<input type="hidden" name="day" value="${day}">

		<input type="hidden" name="beforeMemo1" value="${memo1}">
		<input type="hidden" name="beforeMemo2" value="${memo2}">
		<input type="hidden" name="beforeMemo3" value="${memo3}">


		<button type="submit" formaction="ScheduleRecordServlet">修正する</button>

		<%--memo1,memo2,memo3のいずれかに値が入っているとき削除ボタンを追加させる --%>
		<c:if test="${memo1 != null || memo2 != null || memo3 != null}">
			<button type="submit" formaction="ScheduleClearServlet">シフトを全て削除する</button>
		</c:if>

	</form>

	<%--ポップアップウィンドウ formの処理後に自動的に表示 --%>
	<c:if test="${pop}">
		<div class="modal_wrap">
			<input id="trigger" type="checkbox" checked="checked">
			<div class="modal_overlay">
				<label for="trigger" class="modal_trigger"></label>
				<div class="modal_content">
					<label for="trigger" class="close_button">✖️</label>
					<p class="modal_title2">シフトの修正結果</p>
					<%--resultの結果によって表示内容を分岐 --%>
					<c:choose>
						<c:when test="${modifyResult}">
							<p class="suc-text">修正が完了しました。</p>
						</c:when>
						<c:otherwise>
							<p class="er-text">[エラー] 修正に失敗しました。</p>
							<span class="round_btn"></span>
							<p>もう一度、正しい値を入力してください。</p>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</c:if>

	<br>
	<a href="CalendarServlet">ホームへ戻る</a>

<style>



/*--------------------------------------
  モーダル表示上から_002
--------------------------------------*/
.modal_wrap input {
  display: none;
}

.modal_overlay {
  display: flex;
  justify-content: center;
  overflow: auto;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 9999;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.7);
  opacity: 0;
  transition: opacity 0.5s, transform 0s 0.5s;
  transform: scale(0);
}

.modal_trigger {
  position: absolute;
  width: 100%;
  height: 100%;
}

.modal_content {
    align-self: center;
    width: 60%;
    padding: 30px 30px 15px;
    box-sizing: border-box;
    background: #fff;
    line-height: 1.4em;
    transition: 0.5s;
}

.modal_content p {
  padding-top: 0;
}

.close_button {
  position: absolute;
  top: 14px;
  right: 16px;
  font-size: 24px;
  cursor: pointer;
}

.modal_wrap input:checked ~ .modal_overlay {
  opacity: 1;
  transform: scale(1);
  transition: opacity 0.5s;
}

.modal_wrap input:checked ~ .modal_overlay .modal_content {
  transform: translateY(20px);
}

.open_button {
   color: #4f96f6;
    background-color: #eeeeee;
    font-weight: bold;
    text-align: center;
    cursor :pointer;
    transition: all 0.3s;
    display: block;
    margin-top: 40px;
    margin-bottom: 1px;
    padding: 12px 2px;
    max-width:300px;
    text-decoration: none;
}

.open-button:active {
  /*ボタンを押したとき*/
  -webkit-transform: translateY(2px);
  transform: translateY(2px);/*下に動く*/

}



/*アイコンを表示*/
.open-button:after {
font-family: "Font Awesome 5 Free";
  content: "\f2d0";
    padding-left: 8px;
}

/*ラベルホバー時*/
.open-button:hover {
  color: #FFFFFF;
  background-color: #4f96f6;
  transition: .6s;
}

.modal_title2 {
  font-size: 1.5em;
	position: relative;
	overflow: hidden;
  padding-bottom: 10px;
  margin-top:0;
  margin-bottom: 0;
}

.modal_title2::before,
.modal_title2::after{
	content: "";
	position: absolute;
	bottom: 0;
}

/* h2 プライマリカラー*/
.modal_title2:before{
	border-bottom: 4px solid #6bb6ff;
	width: 100%;
}
/* h2 セカンダリカラー*/
.modal_title2:after{
	border-bottom: 4px solid #c8e4ff;
	width: 100%;
}



/* エラー時のボタン*/
.round_btn {
  display: block;
  position: relative;
  bottom: 30px;
  width: 30px;
  height: 30px;
  border-radius: 50%;  /* 丸みの度合い */
  background: red; /* ボタンの背景色 */
}

.round_btn::before, .round_btn::after {
  content: "";
  position: absolute;
  top: 50%;
  left: 50%;
  width: 3px;
  height: 21px;
  background: #fff; /* バツ印の色 */
}

.round_btn::before {
  transform: translate(-50%,-50%) rotate(45deg);
}

.round_btn::after {
  transform: translate(-50%,-50%) rotate(-45deg);
}

.suc-text{
	font-size: 20px;
}

.er-text{
	position:relative;
	top: 17px;
	left: 39px;
	font-size: 20px;
}

</style>
</body>
</html>