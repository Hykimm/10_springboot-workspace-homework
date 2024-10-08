package com.hy.boot.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.hy.boot.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MemberDao {

	private final SqlSessionTemplate sqlSessionTemplate;
	
	public MemberDto selectMember(MemberDto m) {
		return sqlSessionTemplate.selectOne("memberMapper.selectMember", m);
	}
	
	public int selectUserIdCount(String checkId) {
		return sqlSessionTemplate.selectOne("memberMapper.selectUserIdCount", checkId);
	}
	
	public int insertMember(MemberDto m) {
		return sqlSessionTemplate.insert("memberMapper.insertMember", m);
	}
	
	public int updateProfileImg(MemberDto m) {
		return sqlSessionTemplate.update("memberMapper.updateProfileImg", m);
	}
	
	public int updateMember(MemberDto m) {
		return sqlSessionTemplate.update("memberMapper.updateMember", m);
	}
	
	public int deleteMember(String userId) {
		return sqlSessionTemplate.update("memberMapper.deleteMember", userId);
	}
	
	public int updatePwd(MemberDto member) {
		return sqlSessionTemplate.update("memberMapper.updatePwd", member);
	}
	
}
