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
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	
	<div class="content">
		<br><br>
		
		<div class="innerOuter">
			<h4>WebSocket을 이용해서 실시간으로 통신하기</h4>
			<p>
				실시간으로 알람을 발생시킨다거나
				실시간으로 채팅을 할 때 주로 WebSocket통신방식 이용
				<br><br>
				
				* HTTP 통신(기본통신방식) : 비연결 통신 <br>
				- 한번 요청 보내고 처리되면 연결끊김 == 지속적으로 데이터를 주고받기 불가 <br><br>
				
				* WebSocket 통신 : 영구적 양방향 통신 <br>
				- 실시간으로 연결되어있음 == 지속적으로 데이터 주고받기 가능
 			</p> <br><br>
 			
 			<c:if test="${not empty loginUser}">
 				<a class="btn btn-secondary" href="${contextPath}/chat/room.page">채팅방 입장</a>
 			</c:if>
		</div>		
		
		<br><br>
	</div>

	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>