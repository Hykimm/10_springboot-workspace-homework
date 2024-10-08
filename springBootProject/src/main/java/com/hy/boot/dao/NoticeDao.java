package com.hy.boot.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.hy.boot.dto.AttachDto;
import com.hy.boot.dto.NoticeDto;
import com.hy.boot.dto.PageInfoDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class NoticeDao {

	private final SqlSessionTemplate sqlSessionTemplate;
	
	public int selectNoticeListCount() {
		return sqlSessionTemplate.selectOne("noticeMapper.selectNoticeListCount");
	}
	
	public List<NoticeDto> selectNoticeList(PageInfoDto pi){
		int limit = pi.getBoardLimit(); // 몇개 조회할지
		int offset = (pi.getCurrentPage()-1) * limit; // 몇개의 공지사항 건너뛰고
		RowBounds rowBounds = new RowBounds(offset, limit);
		return sqlSessionTemplate.selectList("noticeMapper.selectNoticeList", null, rowBounds);
	}
	
	public int insertNotice(NoticeDto notice) {
		return sqlSessionTemplate.insert("noticeMapper.insertNotice", notice);
	}
	
	public int insertAttach(AttachDto at) {
		return sqlSessionTemplate.insert("noticeMapper.insertAttach", at);
	}
	
	public NoticeDto selectNotice(int no) {
		return sqlSessionTemplate.selectOne("noticeMapper.selectNotice", no);
	}
	
}
