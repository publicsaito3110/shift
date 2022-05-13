<%@page contentType="text/html; charset=UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page import = "bean.ScheduleBean" %>

<!DOCTYPE HTML>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/login.css">
</head>
<body>
	<h1>ログイン</h1>
	<div class="login-page">
	  <div class="form">
	    <form action="LoginServlet" method="post" class="login-form">
	      <input type="text" name="id" placeholder="ユーザーID"/>
	      <input type="password" name="password" placeholder="パスワード"/>
	      <button type="submit">login</button>
	      <p class="message">${err}</p>
	    </form>
	  </div>
	</div>
</body>
</html>