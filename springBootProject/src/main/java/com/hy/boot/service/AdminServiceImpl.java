package com.hy.boot.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hy.boot.dao.AdminDao;
import com.hy.boot.dto.MemberDto;
import com.hy.boot.dto.PageInfoDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final AdminDao adminDao;

	// 직원관리 조회 서비스(페이징)
	@Override
	public int selectMemberListCount() {
		return adminDao.selectMemberListCount();
	}

	// 직원조회 서비스(직원관리)
	@Override
	public List<MemberDto> selectMemberList(PageInfoDto pi) {
		return adminDao.selectMemberList(pi);
	}

	// 직원관리 페이지 ajax로 직원조회
	@Override
	public int ajaxFilterMemberListCount(MemberDto m) {
		return adminDao.ajaxFilterMemberListCount(m);
	}

	// 직원관리 페이지 ajax로 직원조회
	@Override
	public List<MemberDto> ajaxFilterMemberList(Map<String, Object> map) {
		return adminDao.ajaxFilterMemberList(map);
	}

	// 직원상세페이지
	@Override
	public MemberDto memberDetail(int memNo) {
		return adminDao.memberDetail(memNo);
	}

	// 직원수정기능
	@Override
	public int updateMember(MemberDto m) {
		return adminDao.updateMember(m);
	}
	
}
