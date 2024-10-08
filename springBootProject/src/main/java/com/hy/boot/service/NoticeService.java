package com.hy.boot.service;

import java.util.List;

import com.hy.boot.dto.NoticeDto;
import com.hy.boot.dto.PageInfoDto;

public interface NoticeService {

	// 공지사항 목록페이지 + 페이징처리
	int selectNoticeListCount();
	List<NoticeDto> selectNoticeList(PageInfoDto pi);
	
	// 공지 등록
	int insertNotice(NoticeDto notice);
	
	// 공지사항 상세페이지
	NoticeDto selectNotice(int no);
	
	
}
