package com.hy.boot.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hy.boot.dto.MemberDto;
import com.hy.boot.service.MemberService;
import com.hy.boot.util.FileUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MemberController {

	private final MemberService memberService;
	private final BCryptPasswordEncoder bcryptPwdEncoder;
	private final FileUtil fileUtil;
	
	// * 로그인관련 ---------------------------------------------------------------
	@PostMapping("/signin.do")
	public void signin(MemberDto m
					 , HttpServletRequest request
					 , HttpServletResponse response) throws IOException {
		MemberDto loginUser = memberService.selectMember(m); // 아이디와 비밀번호 가지고 조회된 회원객체
		// 암호화전 : 아이디와 비밀번호 가지고 조회된 회원객체
		// 암호화후 : 오로지 아이디만을 가지고 조회된 회원객체 (=> 후에 비밀번호 확인해야됨)		
		//		   loginUser.getUserPwd() => DB에 저장되어있는 암호문
		//		   매개변수 m.getUserPwd() => 로그인요청시 입력했던 평문 그대로의 비번
		
		// 로그인 성공시 => alert와 함께 메인페이지로 이동
		// 로그인 실패시 => alert와 함께 기존에 보던 페이지 유지
		log.debug("m: {}", m);
		// 실행시킬 script문을 요청했던 페이지로 돌려주는 방식
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.println("<script>"); // if든 else든 스크립트문이 실행되게 스크립트문을 조건문 바깥에 작성
		//if(loginUser != null) { // 암호화전 로그인성공에 대한 조건
		if(loginUser != null && bcryptPwdEncoder.matches(m.getUserPwd(), loginUser.getUserPwd())) { // 암호화후 로그인성공에 대한 조건
			request.getSession().setAttribute("loginUser", loginUser);
			out.println("alert('" + loginUser.getUserName() + "님 환영합니다.');");
			//out.println("location.href='" + request.getContextPath() + "';");
			//out.println("location.href='" + request.getContextPath() + "/board/list.do';");
			out.println("location.href='" + request.getHeader("referer") + "';"); // 이전에보던페이지 재요청
		}else {
			out.println("alert('로그인 실패하였습니다. 아이디 및 비밀번호를 다시 확인해주세요.');");
			out.println("history.back();");
		}
		out.println("</script>");
	}
	
	@RequestMapping("/signout.do")
	public String signout(HttpSession session) {
		session.invalidate(); // 세션 만료시키는 구문
		return "redirect:/";
	}
	
	// * 회원가입 관련 ---------------------------------------------------------------
	@GetMapping("/signup.page")
	public String signupPage() {
		return "member/signup";
	}
	
	@ResponseBody
	@GetMapping("/idCheck.do")
	public String ajaxIdCheck(String checkId) {
		return memberService.selectUserIdCount(checkId) > 0 ? "NNNNN" : "YYYYY";
	}
	
	@PostMapping("/signup.do")
	public String signup(MemberDto member, RedirectAttributes redirectAttributes) {
		//log.debug("암호화전 member: {}", member);
		member.setUserPwd(bcryptPwdEncoder.encode(member.getUserPwd()));
		//log.debug("암호화후 member: {}", member);
		
		int result = memberService.insertMember(member);
		
		// 성공시 => alert와 함께 메인페이지
		// 실패시 => alert와 함께 기존에 작업중이던 페이지
		/*
		 * * case 1)
		 * 				 Model1 생성				Model1 유지
		 * url 요청 ----> controller -----------> 포워딩 jsp
		 * 
		 * 
		 * * case 2)
		 * 				 Model1 생성	 Model1 소멸	 Model2 생성		  Model2 유지
		 * url 요청 ----> controller -----------> controller -----> 포워딩 jsp
		 * 							 redirect
		 * 
		 * * case 3)
		 * 			RedirectAttributes1 생성					RedirectAttributes1 유지
		 * url 요청 ----> controller -----------> controller -----> 포워딩 jsp
		 * 			.addFlashAttribute 데이터 담기
		 */
		redirectAttributes.addFlashAttribute("alertTitle", "회원가입 서비스");
		if(result > 0) {
			redirectAttributes.addFlashAttribute("alertMsg", "성공적으로 회원가입 되었습니다.");
			// => HomtController의 mainPage메소드실행 => main.jsp 포워딩
		}else {
			redirectAttributes.addFlashAttribute("alertMsg", "회원가입에 실패하였습니다.");
			redirectAttributes.addFlashAttribute("historyBackYN", "Y");
		}
		return "redirect:/";
	}
	
	// * 마이페이지 관련 ---------------------------------------------------------------
	@GetMapping("/myinfo.page")
	public String myinfo() {
		return "member/myinfo";
	}
	
	@ResponseBody
	@PostMapping("/modifyProfile.do")
	public String ajaxModifyProfile(MultipartFile uploadFile
								, HttpSession session) {
		MemberDto loginUser = (MemberDto)session.getAttribute("loginUser");
		String originalProfileURL = loginUser.getProfileURL();
		
		// 파일업로드
		Map<String, String> map = fileUtil.fileUpload(uploadFile, "profile");
		loginUser.setProfileURL(map.get("filePath") + "/" + map.get("filesystemName"));
		
		int result = memberService.updateProfileImg(loginUser);
		
		if(result > 0) {
			if(originalProfileURL != null) {
				new File(originalProfileURL).delete();
			}
			return "SUCCESS";
		}else {
			// 실패시 이미 저장된 파일 삭제
			new File(map.get("filePath") + "/" + map.get("filesystemName")).delete();
			return "FAIL";
		}
	}
	
	@PostMapping("/modify.do")
	public String modify(MemberDto member
					   , RedirectAttributes redirectAttributes
					   , HttpSession session) {
		int result = memberService.updateMember(member);
		redirectAttributes.addFlashAttribute("alertTitle", "회원정보수정 서비스");
		
		if(result > 0) {
			redirectAttributes.addFlashAttribute("alertMsg", "성공적으로 정보수정 되었습니다.");
			session.setAttribute("loginUser", memberService.selectMember(member));
			// 다시 로그인시 쿼리문을 호출해서 정보 최신화
		}else {
			redirectAttributes.addFlashAttribute("alertMsg", "정보수정에 실패하였습니다.");
		}
		// 성공시 => alert메세지, 세션에 Member객체 갱신
		// 실패시 => alert메세지
		return "redirect:/member/myinfo.page";
	}
	
	@PostMapping("/leave.do")
	public String leave(String userPwd
					  , HttpSession session
					  , RedirectAttributes redirectAttributes) {
		
		MemberDto loginUser = (MemberDto)session.getAttribute("loginUser");
		// 현재로그인한회원비번이랑 사용자가입력한비번이 일치하는지
		//	session에 담겨있음		userPwd
		redirectAttributes.addFlashAttribute("alertTitle", "회원탈퇴 서비스");
		if(bcryptPwdEncoder.matches(userPwd, loginUser.getUserPwd())) {
			memberService.deleteMember(loginUser.getUserId());
			redirectAttributes.addFlashAttribute("alertMsg", "성공적으로 회원탈퇴 되었습니다. 그동안 이용해주셔서 감사합니다.");
			session.invalidate();
		}else { // 비번을 잘못입력했을 경우
			redirectAttributes.addFlashAttribute("alertMsg", "비밀번호가 틀렸습니다. 다시 입력해주세요.");
			redirectAttributes.addFlashAttribute("historyBackYN", "Y");
		}
		return "redirect:/";
	}
	
	// * 비밀번호 변경 KDT_springFramework 문제---------------------------------------------------------------
	@PostMapping("/modifyPwd.do")
	public String updatePwd(String updatePwd
						  , String userPwd
						  , HttpSession session
						  , RedirectAttributes redirectAttributes) {
		
		MemberDto member = (MemberDto)session.getAttribute("loginUser");
		
		redirectAttributes.addFlashAttribute("alertTitle", "비밀번호 변경 서비스");
		
		if(member != null && bcryptPwdEncoder.matches(userPwd, member.getUserPwd())) {
			member.setUserPwd(bcryptPwdEncoder.encode(updatePwd));
			memberService.updatePwd(member);
			redirectAttributes.addFlashAttribute("alertMsg", "비밀번호를 변경했습니다.");
		}else {
			redirectAttributes.addFlashAttribute("alertMsg", "비밀번호가 틀렸습니다.");
		}
		
		return "redirect:/member/myinfo.page";
		
	}
	// * 비밀번호 변경 KDT_springFramework 문제---------------------------------------------------------------
	
}















