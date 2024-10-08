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
	table *{margin:5px;}
	table{width:100%;}
</style>
</head>
<body>
    
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>
            
            
            <a class="btn btn-secondary" style="float:right" href="${contextPath}/board/list.do">목록으로</a>
            <br><br>
            <table id="contentArea" align="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${board.boardTitle}</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${board.boardWriter}</td>
                    <th>작성일</th>
                    <td>${board.registDt}</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td colspan="3">
                    	<c:forEach var="at" items="${board.attachList}">
                        <a href="${contextPath}${at.filePath}/${at.filesystemName}" download="${at.originalName}">${at.originalName}</a> <br>
                    	</c:forEach>
                    </td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px">${board.boardContent}</p></td>
                </tr>
            </table>
            <br>
						
						<c:if test="${loginUser.userId eq board.boardWriter}">
	            <form id="frm" action="" method="post" align="center">
	            	<input type="hidden" name="no" value="${board.boardNo}">
                <!-- 수정하기, 삭제하기 버튼은 이글이 본인글일 경우만 보여져야됨 -->
                <button type="submit" class="btn btn-primary" onclick="frmSubmit(1);">수정하기</button>
                <button type="submit" class="btn btn-danger" onclick="frmSubmit(2);">삭제하기</button>
	            </form><br><br>
	            <script>
	            	function frmSubmit(num){
	            		$("#frm").attr("action", num==1 ? "${contextPath}/board/modifyForm.page" 
	            																		: "${contextPath}/board/remove.do");	
	            	}
	            </script>
						</c:if>
						
            <table id="replyArea" class="table" align="center">
                <thead>
                		<c:choose>
	                		<c:when test="${empty loginUser}">
		                    <tr>
		                        <th colspan="2">
		                            <textarea class="form-control" readonly id="content" cols="55" rows="2" style="resize:none; width:100%">로그인후 이용가능한 서비스입니다.</textarea>
		                        </th>
		                        <th style="vertical-align: middle"><button class="btn btn-secondary disabled">등록하기</button></th>
		                    </tr>
	                    </c:when>
	                    <c:otherwise>
		                    <tr>
		                        <th colspan="2">
		                            <textarea class="form-control" id="replyContent" cols="55" rows="2" style="resize:none; width:100%"></textarea>
		                        </th>
		                        <th style="vertical-align: middle"><button class="btn btn-secondary" onclick="ajaxInsertReply();">등록하기</button></th>
		                    </tr>
	                    </c:otherwise>
                    </c:choose>
                    <tr>
                       <td colspan="3">댓글 (<span id="rcount"></span>) </td> 
                    </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
        
        <script>
        
        	$(document).ready(function(){
        		ajaxReplyList();
        		
        		// 동적으로 만들어진 요소에 이벤트 걸때 => 이벤트 메소드방식 안됨
        		//$(".removeReply").click(function(){
        	  //$(".removeReply")on("click", function(){
        		//  상위요소						이벤트					해당 요소					실행할 function
        		$(document).on("click", ".removeReply", function(){
        		  // 해당 댓글 삭제용 ajax 요청
        		  console.log("삭제할댓글번호", $(this).data("replyno"));
         		  $.ajax({
        			  url:"${contextPath}/board/removeReply.do",
        			  type:"get",
        			  data:"no=" + $(this).data("replyno"),
        			  success:function(result){
        				  if(result == "SUCCESS"){
        					  ajaxReplyList();
        				  }
        			  },
        			  error:function(){
        				  console.log("댓글 삭제용 ajax 통신 실패");
        			  }
        		  })
        		})
        	})
        	
        	// 현재 게시글의 댓글 리스트를 ajax로 조회해서 뿌리는 fucntion
        	function ajaxReplyList(){
						$.ajax({
							url:"${contextPath}/board/replyList.do",
							type:"get",
							//data:{boardNo:${board.boardNo}}, 이것도 가능
							data:"no=${board.boardNo}",
							success:function(list){
								console.log(list);
								$("#rcount").text(list.length);
								
								let value = "";
								for(let i=0; i<list.length; i++){
									value  += "<tr>"
													+		"<th>" + list[i].replyWriter + "</th>"
													+		"<td>" + list[i].replyContent + "</td>"
													+		"<td>" + list[i].registDt;
									// 현재로그인한 회원이 해당 댓글의 작성자일 경우				
									if(list[i].replyWriter == '${loginUser.userId}'){
										value += "<button class='btn btn-sm btn-danger removeReply' data-replyno='" + list[i].replyNo + "'>삭제</button>"
									}
									value		+= "</td>"
													+	"</tr>";
								}
								$("#replyArea tbody").html(value);
							},
							error:function(){
								console.log("댓글조회용 ajax 통신 실패");
							}
						})
        	}
        	
        	// 댓글 ajax로 작성 요청하는 function
        	function ajaxInsertReply(){
        		
        		if($("#replyContent").val().trim().length != 0){
	        		$.ajax({
	        			url:"${contextPath}/board/registReply.do",
	        			type:"post",
	        			data:{
	        				replyContent:$("#replyContent").val(),
	        				refBoardNo:${board.boardNo}
	        			},
	        			//data:"content=" + $("#replyContent").val() + "&no=${board.boardNo}", 이것도 가능
	        			success:function(result){
	        					if(result == "SUCCESS"){
	        						$("#replyContent").val("");
	        						ajaxReplyList();
	        					}else if(result == "FAIL"){
	        						alertify.alert("댓글 작성 서비스", "다시 입력해주세요.");	
	        					}
	        			},
	        			error:function(){
	        				console.log("댓글 작성용 ajax 통신 실패");
	        			}
	        		})
        		}else{
        			alertify.alert("댓글 작성 서비스", "다시 입력해주세요.");
        		}
        	}
        	
        </script>
        
        <br><br>
    </div>

    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
</body>
</html>