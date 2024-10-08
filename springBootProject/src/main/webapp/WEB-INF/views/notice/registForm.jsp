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
제목 내용 첨부파일 첨부는 다중가능
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	
	
	<div align="center">
		<h2>공지사항 작성</h2>
		<form action="${contextPath}/notice/regist.do" method="post" enctype="multipart/form-data">
			제목:
			<input type="text" name="noticeTitle"><br>
			내용:
			<textarea rows="10" name="noticeContent"></textarea><br>
			첨부파일:
			<input type="file" name="uploadFiles" multiple><br>
			
			<button type="submit">등록</button>
		</form>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	
</body>
</html>