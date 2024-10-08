<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#updateForm>table{width:100%;}
	#updateForm>table *{ margin:5px;}
	.origin_del{cursor:pointer}
</style>
</head>
<body>
    <!-- 이쪽에 메뉴바 포함 할꺼임 -->
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 수정하기</h2>
            <br>

            <form id="updateForm" method="post" action="${contextPath}/board/modify.do" enctype="multipart/form-data">
                <input type="hidden" name="boardNo" value="${board.boardNo}">
                <table align="center">
                    <tr>
                        <th><label for="title">제목</label></th>
                        <td><input type="text" id="title" class="form-control" name="boardTitle" value="${board.boardTitle}" required></td>
                    </tr>
                    <tr>
                        <th><label for="writer">작성자</label></th>
                        <td><input type="text" id="writer" class="form-control" value="${board.boardWriter}" readonly></td>
                    </tr>
                    <tr>
                        <th style="vertical-align:baseline"><label for="upfile">첨부파일</label></th>
                        <td>
                            <input type="file" id="upfile" class="form-control-file border" name="uploadFiles" multiple>
                          
                            <!-- 기존의 첨부파일 목록들 -->
                            <c:forEach var="at" items="${board.attachList}">
                            	<div>
                            		<a href="${contextPath}${at.filePath}/${at.filesystemName}" download="${at.originalName}">${at.originalName}</a>
                            		<span class="origin_del" data-fileno="${at.fileNo}">x</span>
                            	</div>
                            </c:forEach>
                            
                        </td>
                    </tr>
                    <tr>
                        <th colspan="2"><label for="content">내용</label></th>
                    </tr>
                    <tr>
                        <th colspan="2"><textarea class="form-control" required name="boardContent" id="content" rows="10" style="resize:none;">${board.boardContent}</textarea></th>
                    </tr>
                </table>
                <script>
                	$(document).ready(function(){
                		$(".origin_del").on("click", function(){
                			// 삭제하고자 하는 해당 첨부파일 번호를 form submit시 넘기기 위한 작업
                			// => 해당 form요소내에 input type="hidden" 만들어서 append
                			// <input type="hidden" name="delFileNo" value="클릭한첨부파일번호">
                			let inputEl = document.createElement("input");
                			inputEl.type = "hidden";
                			inputEl.name = "delFileNo";
                			inputEl.value = $(this).data("fileno");
                			
                			document.getElementById("updateForm").append(inputEl);
                			
                			// 화면으로부터 사라지도록 작업 선택한요소의 상위요소를 remove
                			$(this).parent().remove();
                		})
                	})
                </script>
                <br>

                <div align="center">
                    <button type="submit" class="btn btn-primary">수정하기</button>
                    <button type="button" class="btn btn-danger" onclick="javascript:history.go(-1);">이전으로</button>
                </div>
            </form>
        </div>
        <br><br>
    </div>

    <!-- 이쪽에 푸터바 포함할꺼임 -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>