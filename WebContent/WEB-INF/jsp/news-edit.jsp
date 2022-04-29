<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/header.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/mordal.css">
</head>
<body>
	<jsp:include page="common/header.jsp" flush="true" />
	<div id="pop-window-wrap"></div>

	<h1 class="title">お知らせ編集</h1>
	<div class="tab-wrap">
		<input id="TAB-01" type="radio" name="TAB" class="tab-switch" checked="checked" /><label class="tab-label" for="TAB-01">登録済みのお知らせ</label>
	<div class="tab-content">
		<jsp:include page="news-recod-list.jsp" flush="true" />
	</div>
    <input id="TAB-02" type="radio" name="TAB" class="tab-switch" /><label class="tab-label" for="TAB-02">現在表示中のお知らせ</label>
    <div class="tab-content">
		<jsp:include page="news-display-list.jsp" flush="true" />
	</div>
    <input id="TAB-03" type="radio" name="TAB" class="tab-switch" /><label class="tab-label" for="TAB-03">お知らせを追加+</label>
    <div class="tab-content">
		<h3>日付: <input type="date" id="news-add-date" value="${nowDate}" min="${nowDate}" max="${maxDate}"></h3>
		<h3>タイトル</h3>
		<textarea id="news-add-title" maxlength="20"></textarea>
		<h3>カテゴリー</h3>
		<select id="news-add-category">
			<c:forEach var="i" items="${newsCtgArray}" varStatus="status">
				<option value="${status.count}">${i}</option>
			</c:forEach>
		</select>
		<h3>本文</h3>
		<textarea id="news-add-content" maxlength="200"></textarea>
		<p><button id="news-add-btn" disabled>新規登録</button></p>
	</div>
</div>

<section id="modalArea-detail" class="modalArea">
  <div id="modalBg" class="modalBg"></div>
  <div class="modalWrapper">
    <div class="modalContents">
      <h1 id="modal-title-text">(タイトル)</h1>
      <h3 id="modal-subtitle-text">(日付)</h3><span><img id="modal-ctg-icon" class="icon-img" alt="category" src=""></span>
      <p id="modal-content-text">(内容)</p>
      <button id="check-news-modify-btn">お知らせを修正する</button>
    </div>
    <div id="closeModal" class="closeModal">
      ×
    </div>
  </div>
</section>

<section id="modalArea-modify" class="modalArea">
	<div class="modalBg"></div>
		<div class="modalWrapper">
			<div class="modalContents">
      			<h2 class="modal-title">タイトル</h2>
				<textarea id="modify-title" maxlength="20"></textarea>
				<h3 id="modify-subtitle">日付</h3>
				<span>
      				<select id="modify-category">
	      				<c:forEach var="i" items="${newsCtgArray}" varStatus="status">
							<option value="${status.count}">${i}</option>
						</c:forEach>
					</select>
				</span>
				<h3 class="modal-content">内容</h3>
				<textarea id="modify-content" maxlength="200"></textarea>
				<button id="news-modify-btn" disabled>修正する</button>
			</div>
			<div id="closeModal-modify" class="closeModal">×</div>
		</div>
</section>
</body>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(function(){

	//liをクリック時の処理
	$('.news-list-btn').on('click', function (){

		$("#modal-subtitle-text").text($(this).children("span").text());
		$("#modal-title-text").text($(this).children("a").text());
		$("#modal-content-text").html($(this).children("input").val());
		var src = $(this).children("span").children("img").attr("src");
		$("#modal-ctg-icon").attr("src", src);

	});

	$('.news-list-btn').click(function(){
		$('#modalArea-detail').fadeIn();
	});
	$('#closeModal , #modalBg, #check-news-modify-btn').click(function(){
		$('#modalArea-detail').fadeOut();
    });

	//修正するボタンをクリック時の処理
	$('#check-news-modify-btn').click(function(){
		$('#modalArea-modify').fadeIn();
		$("#modify-subtitle").text($("#modal-subtitle-text").text());
	});
	$('#closeModal-modify, #news-modify-btn').click(function(){
		$('#modalArea-modify').fadeOut();
    });

	//修正ボタンの処理
	$('#modify-title, #modify-content').on('input', function(){
		if($('#modify-title' && '#modify-content').val()){
			$("#news-modify-btn").prop('disabled', false);
		}else{
			$("#news-modify-btn").prop('disabled', true);
		}
	});

	//修正ボタンを押したとき
	$('#news-modify-btn').on('click', function (){

		$('#modalArea-modify').fadeOut();
		$.ajax({
		url: "NewsModifyServlet",
		type: "POST",
		data: {date : $("#modify-subtitle").text(), category : $("#modify-category").val(), title : $("#modify-title").val(), content : $("#modify-content").val()}
		}).done(function (result) {
			// 通信成功時のコールバック
			$("#pop-window-wrap").html(result);
		}).fail(function () {
			// 通信失敗時のコールバック
			alert("読み込みに失敗しました。");
		}).always(function (result) {
        // 常に実行する処理
		});
	});


	//新規登録ボタンの処理
	$('#news-add-title, #news-add-content').on('input', function(){
		if($('#news-add-title' && '#news-add-content').val()){
			$("#news-add-btn").prop('disabled', false);
		}else{
			$("#news-add-btn").prop('disabled', true);
		}
	});

	//新規登録ボタンを押したとき
	$('#news-add-btn').on('click', function (){

		$.ajax({
		url: "NewsAddServlet",
		type: "POST",
		data: {date : $("#news-add-date").val(), category : $("#news-add-category").val(), title : $("#news-add-title").val(), content : $("#news-add-content").val()}
		}).done(function (result) {
			// 通信成功時のコールバック
			$("#pop-window-wrap").html(result);
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
body {
	margin: auto;
}

/*タブ*/
.tab-wrap {
	background: White;
	box-shadow: 0 0 5px rgba(0,0,0,.1);
	display: flex;
	flex-wrap: wrap;
	overflow: hidden;
	padding: 0 0 20px;
}

.tab-label {
	color: Gray;
	cursor: pointer;
	flex: 1;
	font-weight: bold;
	order: -1;
	padding: 12px 24px;
	position: relative;
	text-align: center;
	transition: cubic-bezier(0.4, 0, 0.2, 1) .2s;
	user-select: none;
	white-space: nowrap;
	-webkit-tap-highlight-color: transparent;
}

.tab-label:hover {
	background: rgba(0, 191, 255,.1);
}

.tab-switch:checked + .tab-label {
	color: DeepSkyBlue;
}

.tab-label::after {
	background: DeepSkyBlue;
	bottom: 0;
	content: '';
	display: block;
	height: 3px;
	left: 0;
	opacity: 0;
	pointer-events: none;
	position: absolute;
	transform: translateX(100%);
	transition: cubic-bezier(0.4, 0, 0.2, 1) .2s 80ms;
	width: 100%;
	z-index: 1;
}

.tab-switch:checked ~ .tab-label::after {
	transform: translateX(-100%);
}

.tab-switch:checked + .tab-label::after {
	opacity: 1;
	transform: translateX(0);
}

.tab-content {
	height:0;
	opacity:0;
	padding: 0 20px;
	pointer-events:none;
	transform: translateX(-30%);
	transition: transform .3s 80ms, opacity .3s 80ms;
	width: 100%;
}

.tab-switch:checked ~ .tab-content {
	transform: translateX(30%);
}

.tab-switch:checked + .tab-label + .tab-content {
	height: auto;
	opacity: 1;
	order: 1;
	pointer-events:auto;
	transform: translateX(0);
}

.tab-wrap::after {
	content: '';
	height: 20px;
	order: -1;
	width: 100%;
}

.tab-switch {
	display: none;
}

#news-add-title {
	resize: none;
    height: 27px;
    width: 60%;
    font-size: 25px;
}

#news-add-content {
	resize: none;
    height: 185px;
    width: 90%;
    font-size: 18px;
}

h3 {
	margin: 10px;
}

/*リスト*/
.info-detail li{
	list-style: none;
	padding: 1rem 1rem;
	border-bottom: solid #999 1px;
	position: relative;
}

.info-detail li:hover{
	background-color: #f6f6f6;
}

.date-text{
	position: relative;
	right: 1%;
	bottom: 4px;
	font-size: 25px;
}

.content-text{
	position: absolute;
	left: 30%;
	top: 19%;
	font-size: 25px;
	color: black;
}

.icon-img {
	position: relative;
    width: 2.5%;
}
/* モーダル */
.modalArea {
  display: none;
  position: fixed;
  z-index: 10; /*サイトによってここの数値は調整 */
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}

.modalBg {
  width: 100%;
  height: 100%;
  background-color: rgba(30,30,30,0.9);
}

.modalWrapper {
  position: absolute;
  top: 50%;
  left: 50%;
  transform:translate(-50%,-50%);
  width: 70%;
  max-width: 500px;
  padding: 10px 30px;
  background-color: #fff;
}

.closeModal {
  position: absolute;
  top: 0.5rem;
  right: 1rem;
  cursor: pointer;
  padding: 3px;
}

button {
  padding: 10px;
  background-color: #fff;
  border: 1px solid #282828;
  border-radius: 2px;
  cursor: pointer;
}

#modal-title-text {
	text-align: center;
	border-bottom: double 5px #ffbf1f;
}

#modal-subtitle-text {
	display: inline-flex;
}

#modify-title {
	resize: none;
    width: 477px;
    height: 25px;
    font-size: 22px;
}

#modal-ctg-icon {
	width: 6%;
    position: relative;
    left: 3%;
    vertical-align: text-bottom;
}

#modify-content {
	resize: none;
    width: 477px;
    height: 140px;
    font-size: 15px;
}

#openModal {
  position: absolute;
  top: 50%;
  left: 50%;
  transform:translate(-50%,-50%);
}
</style>
</html>