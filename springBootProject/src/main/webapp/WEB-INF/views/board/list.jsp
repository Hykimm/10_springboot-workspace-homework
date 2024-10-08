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
	#boardList{text-align: center;}
	#boardList>tbody>tr:hover{cursor:pointer;}
	#pagingArea{width:fit-content;margin:auto;}
	#searchForm{width:80%;margin:auto;}
	#searchForm>*{float:left;margin:5px;}
	.select{width:20%;}
	.text{width:53%;}
	.searchBtn{Width:20%;}
</style>
</head>
<body>
    <!-- 이쪽에 메뉴바 포함 할꺼임 -->
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>

    <div class="content">
        <br><br>
        <div class="innerOuter" style="padding:5% 10%;">
            <h2>게시판</h2>
            <br>
            
            <!-- 로그인후 상태일 경우만 보여지는 글쓰기 버튼-->
            <c:if test="${not empty loginUser}">
	            <a class="btn btn-secondary" style="float:right" href="${contextPath}/board/registForm.page">글쓰기</a>
		          <br>
            </c:if>
            
            <br>
            <table id="boardList" class="table table-hover" align="center">
                <thead>
                  <tr>
                    <th>글번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>조회수</th>
                    <th>작성일</th>
                    <th>첨부파일</th>
                  </tr>
                </thead>
                <tbody>
                	<c:choose>
                		<c:when test="${empty list}">
                			<tr>
                				<td colspan="6">조회된 게시글이없습니다.</td>
                			</tr>
                		</c:when>
                		<c:otherwise>
                			<c:forEach var="b" items="${list}">
                				<tr onclick="location.href='${contextPath}/board/${loginUser.userId==b.boardWriter ? 'detail.do' : 'increase.do'}?no=${b.boardNo}';">
                					<td>${b.boardNo}</td>
	                        <td>${b.boardTitle}</td>
	                        <td>${b.boardWriter}</td>
	                        <td>${b.count}</td>
	                        <td>${b.registDt}</td>
	                        <td>${b.attachCount > 0 ? '★' : ''}</td>
                				</tr>
                			</c:forEach>
                		</c:otherwise>
                	</c:choose>
                </tbody>
            </table>
            <br>

            <div id="pagingArea">
                <ul class="pagination">
                    <li class="page-item ${pi.currentPage==1 ? 'disabled' : ''}"><a class="page-link" href="${contextPath}/board/list.do?page=${pi.currentPage-1}">Previous</a></li>
                    
                    <c:forEach var="p" begin="${pi.startPage}" end="${pi.endPage}">
                    	<li class="page-item ${pi.currentPage == p ? 'disabled' : ''}"><a class="page-link" href="${contextPath}/board/list.do?page=${p}">${p}</a></li>
                    </c:forEach>
                    
                    <li class="page-item ${pi.currentPage==pi.maxPage ? 'disabled' : ''}"><a class="page-link" href="${contextPath}/board/list.do?page=${pi.currentPage+1}">Next</a></li>
                </ul>
            </div>
           
            <br clear="both"><br>
            

            <form id="searchForm" action="${contextPath}/board/search.do" method="get" align="center">
                <input type="hidden" name="page" value="1">
                <div class="select">
                    <select class="custom-select" name="condition">
                        <option value="user_id">작성자</option>
                        <option value="board_title">제목</option>
                        <option value="board_content">내용</option>
                    </select>
                </div>
                <div class="text">
                    <input type="text" class="form-control" name="keyword" value="${search.keyword}">
                </div>
                <button type="submit" class="searchBtn btn btn-secondary">검색</button>
            </form>
            
            <c:if test="${not empty search}">
	            <script>
	            	$(document).ready(function(){
	            		$("#searchForm select").val("${search.condition}");
	            		
	            		// 검색후 페이지일 경우 페이징바의 페이지 클릭시
	            		$("#pagingArea a").on("click", function(){
	            			$("#searchForm input[name=page]").val($(this).text() == "Previous" ? ${pi.currentPage-1} : $(this).text() == "Next" ? ${pi.currentPage+1} : $(this).text()); //.val($(this).text()==Previous && $(this).text()==Next ? $(this).text() : "") // .val($(this).text());
	            			$("#searchForm").submit();
	            			//location.href = '${contextPath}/baoard/search.do?condition=${search.condition}&keyword=${search.keyword}&page=${page}'
	            			return false; // 기본이벤트 제거 (즉, a태그에 작성되어있는 href="/list.do" 실행 안되도록)
	            		})
	            	})
	            </script>
            </c:if>
            <br><br>
        </div>
        <br><br>
    </div>

    <!-- 이쪽에 푸터바 포함할꺼임 -->
    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>