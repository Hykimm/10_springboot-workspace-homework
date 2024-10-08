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
	
	<h2>공지사항 상세</h2>
	
	<table id="noticeDetail" align="center">
		<tr>
			<td>제목</td>
			<td>${notice.noticeTitle}</td>
		</tr>
		<tr>
			<td>작성자</td>
			<td>${notice.noticeWriter}</td>
		</tr>
		<tr>
			<td>내용</td>
			<td>${notice.noticeContent}</td>
		</tr>
		<tr>
			<td>작성일</td>
			<td>${notice.registDate}</td>
		</tr>
		<tr>
			<td>첨부파일</td>
			<td>
				<c:forEach var="at" items="${notice.attachList}">
					<a href="${contextPath}${at.filePath}/${at.filesystemName}" download="${at.originalName}">${at.originalName}</a><br>
				</c:forEach>
			</td>
		</tr>
	</table>
	
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>