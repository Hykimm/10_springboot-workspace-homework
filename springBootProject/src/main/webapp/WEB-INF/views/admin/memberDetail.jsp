<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
	<div class="content">
	
		<br><br>
		<div class="innerOuter" style="padding:5% 10%;">
		
			<form action="${contextPath}/admin/updateMember.do" method="post">	
				<h2>직원정보</h2><br>
				직원번호: ${m.userNo}<br>
				이름: <input type="text" name="userName" value="${m.userName}" required><br>
				전화번호: <input type="text" name="phone" value="${m.phone}" required><br>
				주소: <input type="text" name="address" value="${m.address}" required><br><br><br>
				<input type="hidden" name="userNo" value="${m.userNo}">
				
				<button type="submit" class="btn btn-secondary">수정</button>
				<button type="button" class="btn btn-secondary" onclick="history.back();">뒤로가기</button>
			</form>
		</div>
		
	</div>
	
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>