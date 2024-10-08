<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- jQuery 라이브러리 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<!-- 부트스트랩에서 제공하고 있는 스타일 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<!-- 부트스트랩에서 제공하고 있는 스크립트 -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">

<script src="${contextPath}/resources/alertify/js/alertify.min.js"></script>
<link href="${contextPath}/resources/alertify/css/alertify.min.css" rel="stylesheet">
<link href="${contextPath}/resources/alertify/css/default.min.css" rel="stylesheet">
<link href="${contextPath}/resources/alertify/css/semantic.min.css" rel="stylesheet">
</head>
<body>

	<script>
		if("${alertMsg}" != ""){ // 어떤 메시지 문구가 존재할 경우
			alertify.alert("${alertTitle}", "${alertMsg}", function(){
				if("${historyBackYN}" == "Y"){
					history.back();
				}
			});
		}
		
		
	</script>

  <div id="header">
      <div id="header_1">
          <div id="header_1_left">
              <img src="${contextPath}/resources/images/logo.png" alt="로고이미지">
          </div>
          <div id="header_1_center"></div>
          <div id="header_1_right">
          		<c:choose>
          			<c:when test="${empty loginUser}">
		              <a href="${contextPath}/member/signup.page">회원가입</a> | 
		              <a data-toggle="modal" data-target="#loginModal">로그인</a> 
              	</c:when>
              	<c:otherwise>
		              <img src="${contextPath}<c:out value='${loginUser.profileURL}' default='/resources/images/defaultProfile.png'/>">&nbsp;
		              <span>${loginUser.userName}</span> &nbsp;|&nbsp;
		              <a href="${contextPath}/member/myinfo.page">마이페이지</a>
		              <a href="${contextPath}/member/signout.do">로그아웃</a>
		              <c:if test="${not empty loginUser and loginUser.status eq 'A'}">
		              	<a href="${contextPath}/admin/memberList.do">직원관리</a>
		              </c:if>          
		            </c:otherwise>
              </c:choose>
          </div>
      </div>
      <div id="header_2">
          <ul>
              <li><a href="/">HOME</a></li>
              <li><a href="${contextPath}/notice/list.do">공지사항</a></li>
              <li><a href="${contextPath}/board/list.do">자유게시판</a></li>
          </ul>
      </div>
  </div>

  <!-- 로그인 클릭 시 뜨는 모달 (기존에는 안보이다가 위의 a 클릭시 보임) -->
  <div class="modal fade" id="loginModal">
      <div class="modal-dialog modal-sm">
          <div class="modal-content">
          <!-- Modal Header -->
          <div class="modal-header">
              <h4 class="modal-title">Login</h4>
              <button type="button" class="close" data-dismiss="modal">&times;</button> 
          </div>

          <form action="${contextPath}/member/signin.do" method="post">
              <!-- Modal Body -->
              <div class="modal-body">
                  <label for="userId" class="mr-sm-2">ID :</label>
                  <input type="text" class="form-control mb-2 mr-sm-2" placeholder="Enter ID" id="userId" name="userId" required> <br>
                  <label for="userPwd" class="mr-sm-2">Password:</label>
                  <input type="password" class="form-control mb-2 mr-sm-2" placeholder="Enter password" id="userPwd" name="userPwd" required>
              </div>
              
              <!-- Modal footer -->
              <div class="modal-footer">
                  <button type="submit" class="btn btn-primary">로그인</button>
                  <button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
              </div>
          </form>
          </div>
      </div>
  </div>

  <br clear="both">
</body>
</html>