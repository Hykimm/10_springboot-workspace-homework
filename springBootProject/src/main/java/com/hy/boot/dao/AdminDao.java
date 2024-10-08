package com.hy.boot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.hy.boot.dto.MemberDto;
import com.hy.boot.dto.PageInfoDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class AdminDao {
	
	private final SqlSessionTemplate sqlSessionTemplate;
	
	// 직원관리 조회 서비스(페이징)
	public int selectMemberListCount() {
		return sqlSessionTemplate.selectOne("adminMapper.selectMemberListCount");
	}
	
	// 직원조회 서비스(직원관리)
	public List<MemberDto> selectMemberList(PageInfoDto pi){
		int limit = pi.getBoardLimit(); // 몇개 조회할건지
		int offset = (pi.getCurrentPage()-1) * limit; // 몇개의 게시글 건너뛰고
		RowBounds rowBounds = new RowBounds(offset, limit);
		return sqlSessionTemplate.selectList("adminMapper.selectMemberList", null, rowBounds);
	}
	
	// 직원관리 페이지 ajax로 직원조회(페이징)
	public int ajaxFilterMemberListCount(MemberDto m) {
		return sqlSessionTemplate.selectOne("adminMapper.ajaxFilterMemberListCount", m);
	}
	
	// 직원관리 페이지 ajax로 직원조회
	public List<MemberDto> ajaxFilterMemberList(Map<String, Object> map){
		PageInfoDto pi = (PageInfoDto)map.get("pi");
		int limit = pi.getBoardLimit();
		int offset = (pi.getCurrentPage()-1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		return sqlSessionTemplate.selectList("adminMapper.ajaxFilterMemberList", map, rowBounds);
	}

	// 직원상세페이지
	public MemberDto memberDetail(int memNo) {
		return sqlSessionTemplate.selectOne("adminMapper.memberDetail", memNo);
	}
	
	// 직원수정기능
	public int updateMember(MemberDto m) {
		return sqlSessionTemplate.update("adminMapper.updateMember", m);
	}
	
}
