package com.hy.boot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hy.boot.dto.MemberDto;
import com.hy.boot.dto.PageInfoDto;
import com.hy.boot.service.AdminService;
import com.hy.boot.util.PagingUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminController {

	private final AdminService adminService;
	private final PagingUtil pagingUtil;
	
	// 직원관리 페이지
	@GetMapping("/memberList.do")
	public String memberList(@RequestParam(value="page", defaultValue="1") int currentPage, Model model) {
		int listCount = adminService.selectMemberListCount();
		PageInfoDto pi = pagingUtil.getPageInfoDto(listCount, currentPage, 5, 10);
		List<MemberDto> mList = adminService.selectMemberList(pi);
		model.addAttribute("mList", mList);
		model.addAttribute("pi", pi);
		log.debug("pi는? {}", pi);
		return "admin/memberList";
	}
	
	// 직원관리 페이지에서 필터 및 검색으로 직원조회
	@ResponseBody
	@GetMapping(value="/ajaxFilterMemberList.do", produces="application/json; charset=utf-8")
	public Map<String, Object> ajaxFilterMemberList(MemberDto m, @RequestParam(value = "page", defaultValue = "1") int currentPage){
		Map<String, Object> map = new HashMap<>();
		int listCount = adminService.ajaxFilterMemberListCount(m);
		PageInfoDto pi = pagingUtil.getPageInfoDto(listCount, currentPage, 5, 10);
		map.put("m", m);
		map.put("pi", pi);
		List<MemberDto> mFilterList = adminService.ajaxFilterMemberList(map);
		//log.debug("멤버 status는? {}", m.getStatus());
		map.put("mFilterList", mFilterList);
		return map;
	}
	
	// 직원상세페이지
	@GetMapping("/memberDetail.do")
	public String memberDetail(int memNo, Model model) {
		MemberDto m = adminService.memberDetail(memNo);
		model.addAttribute("m", m);
		return "admin/memberDetail";
	}
	
	
	// 직원수정페이지
	@PostMapping("/updateMember.do")
	public String updateMember(MemberDto m, RedirectAttributes redirectAttributes) {
		int result = adminService.updateMember(m);
		if(result > 0) {
			redirectAttributes.addFlashAttribute("alertMsg", "직원정보 수정 완료");
		}else {
			redirectAttributes.addFlashAttribute("alertMsg", "직원정보 수정실패.");
		}
		log.debug("result는: {}",result);
		return "redirect:/admin/memberDetail.do?memNo=" + m.getUserNo();
	}
	
}
