package com.hy.boot.service;

import java.util.List;
import java.util.Map;

import com.hy.boot.dto.MemberDto;
import com.hy.boot.dto.PageInfoDto;

public interface AdminService {

	// 직원관리 조회 서비스(페이징)
	int selectMemberListCount();
	// 직원조회 기능(직원관리)
	List<MemberDto> selectMemberList(PageInfoDto pi);
	
	// 직원관리 페이지 ajax로 직원조회(페이징)
	int ajaxFilterMemberListCount(MemberDto m);
	// 직원관리 페이지 ajax로 직원조회
	List<MemberDto> ajaxFilterMemberList(Map<String, Object> map);
	
	// 직원상세페이지
	MemberDto memberDetail(int memNo);
	
	// 직원수정기능
	int updateMember(MemberDto m);
}
