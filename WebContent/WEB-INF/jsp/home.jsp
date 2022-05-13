<%@page contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import = "bean.ScheduleBean" %>

<!DOCTYPE HTML>

<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/common/header.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/home.css">
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
	            <span class="date-text">${i.ymdFormatDisplayDate}</span>
	            <span>
	            <c:if test="${i.labelNew != null}">
	            	<img class="icon-img" alt="new" src="./png/new-icon.png">
	            </c:if>
	            </span>
	            <span class="ctg-icon-wrap">
	            	<img class="icon-img" alt="category" src="./png/news-ctg-icon${i.category}.png">
	            </span>
	            <a class="content-text">${i.title}</a>
	            <input type="hidden" value="${i.contentAfterBreakLine}">
	        </li>
	        </c:forEach>
	    </ul>
	</div>

	<section id="modalArea-detail" class="modalArea">
	  <div id="modalBg" class="modalBg"></div>
	  <div class="modalWrapper">
	    <div class="modalContents">
	      <h1 id="modal-title-text">(タイトル)</h1>
	      	<h3 id="modal-subtitle-text">(日付)</h3><span><img id="modal-ctg-icon" class="icon-img" alt="category" src=""></span>
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
		$("#modal-content-text").html($(this).children("input").val());
		var src = $(this).children(".ctg-icon-wrap").children("img").attr("src");
		$("#modal-ctg-icon").attr("src", src);

	});

	$('.news-list-btn').click(function(){
		$('#modalArea-detail').fadeIn();
	});
	$('#closeModal , #modalBg').click(function(){
		$('#modalArea-detail').fadeOut();
    });
});
</script>
</html>