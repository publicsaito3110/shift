<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/header.css">
</head>
<body>
	<jsp:include page="common/header.jsp" flush="true" />

	<c:forEach var="i" items=""${userList}>

	</c:forEach>
</body>
</html>