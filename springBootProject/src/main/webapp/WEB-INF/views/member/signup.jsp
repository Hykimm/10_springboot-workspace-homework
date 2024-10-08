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
	.smallfont{font-size:0.8em;}
	.noCheck{display:none;}
	.usable{color:green;}
	.unusable{color:red;}
</style>
</head>
<body>
    
    <jsp:include page="/WEB-INF/views/common/header.jsp"/>
    
    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>회원가입</h2>
            <br>

            <form action="${contextPath}/member/signup.do" method="post" id="signup_form">
                <div class="form-group">
                    <label for="userId">* ID :</label>
                    <input type="text" class="form-control" id="userId" name="userId" placeholder="Please Enter ID" required>
                    <div id="idCheck_result" class="noCheck smallfont"></div>
                    <br>
                    
                    <label for="userPwd">* Password :</label>
                    <input type="password" class="form-control" id="userPwd" name="userPwd" placeholder="Please Enter Password" required>
                    <div id="pwdCheck_result" class="noCheck smallfont"></div>
                    <br>
                    
                    <label for="checkPwd">* Password Check :</label>
                    <input type="password" class="form-control" id="checkPwd" placeholder="Please Enter Password" required>
                    <div id="pwdEqualCheck_result" class="noCheck smallfont"></div>
                    <br>
                    
                    <label for="userName">* Name :</label>
                    <input type="text" class="form-control" id="userName" name="userName" placeholder="Please Enter Name" required>
                    <div id="nameCheck_result" class="noCheck smallfont"></div>
                    <br>
                    
                    <label for="email"> &nbsp; Email :</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="Please Enter Email"><br>
                    
                    <label for="phone"> &nbsp; Phone :</label>
                    <input type="tel" class="form-control" id="phone" name="phone" placeholder="Please Enter Phone (-없이)"><br>
                    
                    <label for="address"> &nbsp; Address :</label>
                    <input type="text" class="form-control" id="address" name="address" placeholder="Please Enter Address"><br>
                    
                    <label for=""> &nbsp; Gender : </label> &nbsp;&nbsp;
                    <input type="radio" name="gender" id="Male" value="M">
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" name="gender" id="Female" value="F">
                    <label for="Female">여자</label><br>
                    
                </div>
                <br>
                <div class="btns" align="center">
                    <button type="submit" class="btn btn-primary" disabled>회원가입</button>
                    <button type="reset" class="btn btn-danger"> 초기화</button>
                </div>
            </form>
        </div>
        <br><br>
    </div>

		<script>
		
			let idResult = false;
			let pwdResult = false;
			let pwdEqualResult = false;
			let nameResult = false;
		
			$(document).ready(function(){
				$("#signup_form input[name=userId]").on("keyup", function(){ // id check start
					let regExp = /^[a-z\d]{5,12}$/; // 아이디형식: 영문자,숫자 5~12자 \d => 0-9
					
					if( $(this).val().trim().length == 0 ){ // 쓰여져있는게 없을 경우,  trim() => 좌우 공백 지워주는 메소드
						// case 1
						idResult =  checkPrint("#idCheck_result", "usable unusable", "noCheck", "");
					}else{ // 쓰여져있는게 있을 경우 => 정규식 검사
						if(regExp.test($(this).val() ) ){ // 정규식패턴에 만족할 경우
							// case 2 (아이디 중복체크 => ajax)
							$.ajax({
								url:"${contextPath}/member/idCheck.do",
								type:"get",
								async:false, // 동기식으로 처리되도록 하는 속성 (순차적으로 진행)
								data:"checkId=" + $(this).val(),
								success:function(result){
									if(result == "YYYYY"){
										idResult = checkPrint("#idCheck_result", "noCheck unusable", "usable", "사용가능한 아이디입니다.");
									}else if(result = "NNNNN"){
										idResult = checkPrint("#idCheck_result", "noCheck usable", "unusable", "중복된 아이디가 존재합니다. 다시 입력해주세요");
									}
								}
							  ,error:function(){
								  console.log("아이디 중복체크용 ajax 통신 실패");
							  }
							}) // ajax end
						}else{ // 정규식패턴에 만족하지 않을 경우
							// case 3
							idResult = checkPrint("#idCheck_result", "noCheck usable", "unusable", "영문, 숫자 포함 5~12자리로 작성해주세요.");
						}
					}
					validate();
				}) // id check end
				
				$("#signup_form input[name=userPwd]").on("keyup", function(){ // pwd check start
					let regExp = /^[a-z\d!@#$%^&*]{8,15}$/;
					
					if($(this).val().trim().length == 0){
						pwdResult = checkPrint("#pwdCheck_result", "usable unusable", "noCheck", "");
					}else{
						if( regExp.test($(this).val()) ){
							pwdResult = checkPrint("#pwdCheck_result", "noCheck unusable", "usable", "사용가능한 비밀번호입니다.");
						}else{
							pwdResult = checkPrint("#pwdCheck_result", "noCheck usable", "unusable", "영문자, 숫자, 특수문자로 8~15자로 작성해주세요.");
						}
					}
					validate();
				}) // pwd check end
				
				$("#signup_form input[id=checkPwd]").on("keyup", function(){ // pwd equal check start
					if($(this).val().trim().length == 0){
						pwdEqualResult = checkPrint("#pwdEqualCheck_result", "usable unusable", "noCheck", "");
					}else{
						if( $(this).val() == $("#signup_form input[name=userPwd]").val() ){
							pwdEqualResult = checkPrint("#pwdEqualCheck_result", "noCheck unusable", "usable", "비밀번호가 일치합니다.");
						}else{
							pwdEqualResult = checkPrint("#pwdEqualCheck_result", "noCheck usable", "unusable", "비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
						}
					}
					validate();
				}) // pwd equal check end
				
				$("#signup_form input[name=userName]").on("keyup", function(){ // name check start
					let regExp = /^[가-힣]{2,5}$/;
					
					if($(this).val().trim().length == 0){
						nameResult = checkPrint("#nameCheck_result", "usable unusable", "noCheck", "");
					}else{
						if( regExp.test($(this).val()) ){
							nameResult = checkPrint("#nameCheck_result", "noCheck unusable", "usable", "사용가능한 이름입니다.");
						}else{
							nameResult = checkPrint("#nameCheck_result", "noCheck usable", "unusable", "한글로 2글자에서 5글자로 작성해주세요.");
						}
					}
					validate();
				}) // name check end
				
			})
			
			function checkPrint(selector, rmClassNm, addClassNm, msg){
				$(selector).removeClass(rmClassNm).addClass(addClassNm).text(msg);
				// 선택자,				지울 클래스,								추가할클래스,					추가할 텍스트문구
				return addClassNm == "usable" ? true : false;
			}
			
			function validate(){
				console.log(idResult, pwdResult, pwdEqualResult, nameResult)
				if(idResult && pwdResult && pwdEqualResult && nameResult){
					$("#signup_form :submit").removeAttr("disabled");
				}else{
					$("#signup_form :submit").attr("disabled", true);
				}
			}
			
		</script>

    <jsp:include page="/WEB-INF/views/common/footer.jsp"/>
    
</body>
</html>