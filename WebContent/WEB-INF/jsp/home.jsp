<%@page contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import = "bean.ScheduleBean" %>

<!DOCTYPE HTML>

<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/header.css">
</head>
<body>
	<jsp:include page="common/header.jsp" flush="true" />


	<h1>${firstLoginText}${userNameText}</h1>

	<div>
		<table>
			<thead>
				<tr>
					<th class="title">お知らせ</th>
				</tr>
			</thead>
		</table>
	</div>

	<div>
	    <ul class="info-detail">
	    	<c:forEach var="i" items="${newsList}" varStatus="status">
	        <li class="news-list-btn">
	            <span class="date-text">${i.ymd}</span>
	            <span class="label">
	            <c:if test="${i.labelNew != null}">
	            	<img class="icon-img" alt="new" src="./png/new-icon.png">
	            </c:if>
	            </span>
	            <span><img class="icon-img" alt="new" src="./png/news-ctg-icon1.png"></span>
	            <a class="content-text">${i.title}</a>
	            <input type="hidden" value="${i.content}">
	        </li>
	        </c:forEach>
	    </ul>
	</div>

	<section id="modalArea-detail" class="modalArea">
	  <div id="modalBg" class="modalBg"></div>
	  <div class="modalWrapper">
	    <div class="modalContents">
	      <h1 id="modal-title-text">(タイトル)</h1>
	      <h3 id="modal-subtitle-text">(日付)</h3>
	      <p id="modal-content-text">(内容)</p>
	    </div>
	    <div id="closeModal" class="closeModal">
	      ×
	    </div>
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
		$("#modal-content-text").text($(this).children("input").val());

	});

	$('.news-list-btn').click(function(){
		$('#modalArea-detail').fadeIn();
	});
	$('#closeModal , #modalBg').click(function(){
		$('#modalArea-detail').fadeOut();
    });
});
</script>

<style>
body {
	margin: auto;
}
.title{
	border-left: solid 15px #333;
	background: #999;
	margin-bottom: 5px;
	padding: 0.5rem;
	width: 0.1%;
	color: #fff;
}
.info-detail li{
	list-style: none;
	padding: 1rem 1rem;
	border-bottom: solid #999 1px;
	position: relative;
}

.date-text{
	position: relative;
	right: 1%;
	bottom: 4px;
	font-size: 25px;
}

.label{

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
#openModal {
  position: absolute;
  top: 50%;
  left: 50%;
  transform:translate(-50%,-50%);
}
</style>
</html>