package com.hy.boot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hy.boot.dto.AttachDto;
import com.hy.boot.dto.MemberDto;
import com.hy.boot.dto.NoticeDto;
import com.hy.boot.dto.PageInfoDto;
import com.hy.boot.service.NoticeService;
import com.hy.boot.util.FileUtil;
import com.hy.boot.util.PagingUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;
	private final PagingUtil pagingUtil;
	private final FileUtil fileUtil;
	
	@GetMapping("/list.do")
	public ModelAndView list(@RequestParam(value="page", defaultValue="1") int currentPage
							, ModelAndView mv) {
		int listcount = noticeService.selectNoticeListCount();
		PageInfoDto pi = pagingUtil.getPageInfoDto(listcount, currentPage, 5, 5);
		List<NoticeDto> list = noticeService.selectNoticeList(pi);
		mv.addObject("pi", pi)
		  .addObject("list", list)
		  .setViewName("notice/list");
		return mv;
	}
	
	@GetMapping("/registForm.page")
	public String registForm() {
		return "notice/registForm";
	}
	
	@PostMapping("/regist.do")
	public String regist(NoticeDto notice
					   , List<MultipartFile> uploadFiles
					   , HttpSession session
					   , RedirectAttributes redirectAttributes) {
		
		MemberDto member = (MemberDto)session.getAttribute("loginUser");
		notice.setNoticeWriter(String.valueOf(member.getUserNo()));
		
		List<AttachDto> attachList = new ArrayList<>();
		
		for(MultipartFile uploadFile : uploadFiles) {
			if(uploadFile != null && !uploadFile.isEmpty()) {
				Map<String, String> map = fileUtil.fileUpload(uploadFile, "notice");
				attachList.add(
							AttachDto.builder()
								 	 .filePath(map.get("filePath"))
								 	 .filesystemName(map.get("filesystemName"))
								 	 .originalName(map.get("originalName"))
								 	 .refType("N")
								 	 .build()
						);
			}
		}
		notice.setAttachList(attachList);
		int result = noticeService.insertNotice(notice);
		
		redirectAttributes.addFlashAttribute("alertTitle", "공지사항 작성");
		if(attachList.isEmpty() && result == 1 || !attachList.isEmpty() && result == attachList.size()) {
			redirectAttributes.addFlashAttribute("alertMsg", "공지사항 작성 성공");
		}
		return "redirect:/notice/list.do";
		
	}
	
	@GetMapping("/detail.do")
	public String detail(int no, Model model) {
		model.addAttribute("notice", noticeService.selectNotice(no));
		return "notice/detail";
	}
	
}
