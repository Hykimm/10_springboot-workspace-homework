<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>직원관리</title>
<style>
	.memberTable>tbody>tr:hover{cursor:pointer;}
	#pagingArea, .memberTable{width:fit-content;margin:auto;}
	#searchName{width: 250px; margin:auto;}
	.status{margin:outo;}
</style>
</head>
<body>

	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	
	<div class="content">
		<br><br>
		<div class="innerOuter" style="padding:5% 10%;">
			<h2>직원관리</h2>
			<br>
			
			<span class="status">
				전체
				<input type="radio" id="all" value="" name="status">
				관리자
				<input type="radio" id="a" value="A" name="status">
				직원
				<input type="radio" id="y" value="Y" name="status">
			</span>
			
			<table class="memberTable">
				<thead class="table tableThead" align="center">
					<tr>
						<th></th>
						<th>직원번호</th>
						<th>이름</th>
						<th>이메일</th>
						<th>주소</th>
						<th>가입일</th>
						<th>전화번호</th>
					</tr>
				</thead>
				<tbody class="table tableTbody" align="center">
					<c:choose>
						<c:when test="${empty mList}">
							<tr>
								<td colspan="7">직원이 없습니다.</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="m" items="${mList}">
								<tr onclick="location.href='${contextPath}/admin/memberDetail.do?memNo=${m.userNo}'">
									<td>
										<img src="${contextPath}<c:out value="${m.profileURL}" default='/resources/images/defaultProfile.png'/>" style="width: 20px; height: 20px;" />
									</td>
									<td>${m.userNo}</td>
									<td>${m.userName}</td>
									<td>${empty m.email ? '-' : m.email}</td>
									<td>${empty m.address ? '-' : m.address}</td>
									<td>${m.signupDt}</td>
									<td>${empty m.phone ? '-' : m.phone}</td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<br>
			
			<c:if test="${pi.listCount > pi.boardLimit}">
				<div id="pagingArea">
					<ul class="pagination">
						<li class="page-item ${pi.currentPage==1 ? 'disabled' : ''}"><a class="page-link" href="${contextPath}/admin/memberList.do?page=${pi.currentPage-1}">Previous</a></li>
	                    
	          <c:forEach var="p" begin="${pi.startPage}" end="${pi.endPage}">
	          	<li class="page-item ${pi.currentPage == p ? 'disabled' : ''}"><a class="page-link" href="${contextPath}/admin/memberList.do?page=${p}">${p}</a></li>
	          </c:forEach>
	          
	          <li class="page-item ${pi.currentPage==pi.maxPage ? 'disabled' : ''}"><a class="page-link" href="${contextPath}/admin/memberList.do?page=${pi.currentPage+1}">Next</a></li>
					</ul>
				</div>
			</c:if>
			
			<input id="searchName" type="text" class="form-control searchName" placeholder="이름으로 검색">
			
		</div>
		<br><br>
		
		<script>
		
			$(document).ready(function(){
				$("#all").attr("checked", true);
			});

		
			// 이름으로 직원검색 ajax 함수호출 메소드(키업이벤트 활용)
			$(document).on("keyup change", ".searchName, .status", function(){
				memberFilter(1);
			});
			
			// 이름으로 직원검색 ajax 함수
			function memberFilter(page){
				let userName = $(".searchName").val();
				let status = $("input[name='status']:checked").val();
				
				$.ajax({
					url:"${contextPath}/admin/ajaxFilterMemberList.do",
					type:"get",
					data:"userName=" + userName + "&page=" + page + "&status=" + status,
					success:function(map){
						$(".tableTbody").empty();
						$(".pagination").empty();
						let filterTable = "";
						let filterPage = "";
						
						if(map.mFilterList.length == 0){
							filterTable +=	'<tr>'
													+			'<td colspan="7">직원이 없습니다.</td>'
													+		'</tr>';
						}else{
							for(let i=0; i<map.mFilterList.length; i++){
								filterTable +=	'<tr onclick=location.href="${contextPath}/admin/memberDetail.do?memNo=' + map.mFilterList[i].userNo + '">'
														+			'<td>'
														+				'<img src="${contextPath}<c:out value="${m.profileURL}" default='/resources/images/defaultProfile.png'/>" style="width: 20px; height: 20px;" />'
														+			'</td>'
														+			'<td>' + map.mFilterList[i].userNo + '</td>'
														+			'<td>' + map.mFilterList[i].userName + '</td>'
														+			'<td>' + (map.mFilterList[i].email ? map.mFilterList[i].email : '-') + '</td>'
														+			'<td>' + (map.mFilterList[i].address ? map.mFilterList[i].address : '-') + '</td>'
														+			'<td>' + map.mFilterList[i].signupDt + '</td>'
														+			'<td>' + (map.mFilterList[i].phone ? map.mFilterList[i].phone : '-') + '</td>'
													  +		'</tr>';
							}
						}
						
						$(".tableTbody").append(filterTable);
						
			      // 페이징처리
						if(map.pi.listCount > map.pi.boardLimit){
							filterPage += "<li class='page-item " + (map.pi.currentPage == 1 ? 'disabled' : '') + "'>"
													+		"<a class='page-link link' onclick='memberFilter(" + (map.pi.currentPage-1) + ");'>Previous"
													+			"<span aria-hidden='true'>"
													+				"<i class='ti ti-chevrons-left fs-4'></i>"
													+			"</span>"
													+		"</a>"
													+	"</li>";
							
							for (let p=map.pi.startPage; p<=map.pi.endPage; p++) {
								filterPage += "<li class='page-item " + (map.pi.currentPage == p ? 'disabled' : '') + "'>"
			                      +   "<a class='page-link link' onclick='memberFilter(" + p + ");' >"
			                      +    	p
			                      +   "</a>"
			                      + "</li>";
							}
							
							filterPage += "<li class='page-item " + (map.pi.currentPage == map.pi.maxPage ? 'disabled' : '') + "'>"
			                   +    "<a class='page-link link' onclick='memberFilter(" + (map.pi.currentPage+1) + ");'>Next"
			                   +       "<span aria-hidden='true'>"
			                   +          "<i class='ti ti-chevrons-right fs-4'></i>"
			                   +        "</span>"
			                   +    "</a>"
			                   + 	"</li>";
			        $(".pagination").append(filterPage);
						}
						
					},
					error:function(){
						console.log("직원검색 ajax 함수 호출 실패");
					}
				})
				
			}
			
		</script>
		
	</div>
	
	<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>