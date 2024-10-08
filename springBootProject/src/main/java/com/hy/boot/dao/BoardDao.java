package com.hy.boot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.hy.boot.dto.AttachDto;
import com.hy.boot.dto.BoardDto;
import com.hy.boot.dto.PageInfoDto;
import com.hy.boot.dto.ReplyDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BoardDao {

	private final SqlSessionTemplate sqlSessionTemplate;
	
	public int selectBoardListCount() {
		return sqlSessionTemplate.selectOne("boardMapper.selectBoardListCount");
	}
	
	public List<BoardDto> selectBoardList(PageInfoDto pi){
		int limit = pi.getBoardLimit(); // 몇개 조회할건지
		int offset = (pi.getCurrentPage()-1) * limit; // 몇개의 게시글 건너뛰고
		RowBounds rowBounds = new RowBounds(offset, limit);
		return sqlSessionTemplate.selectList("boardMapper.selectBoardList", null, rowBounds);
	}
	
	public int selectSearchListCount(Map<String, String> search) {
		return sqlSessionTemplate.selectOne("boardMapper.selectSearchListCount", search);
	}
	
	public List<BoardDto> selectSearchList(Map<String, String> search, PageInfoDto pi){
		RowBounds rowBounds = new RowBounds((pi.getCurrentPage()-1) * pi.getBoardLimit(), pi.getBoardLimit());
		return sqlSessionTemplate.selectList("boardMapper.selectSearchList", search, rowBounds);
																	//  sql문 완성을 위한 맵객체
	}
	
	public int insertBoard(BoardDto b) {
		return sqlSessionTemplate.insert("boardMapper.insertBoard", b);
	}
	
	public int insertAttach(AttachDto at) {
		return sqlSessionTemplate.insert("boardMapper.insertAttach", at);
	}
	
	public int updateIncreaseCount(int boardNo) {
		return sqlSessionTemplate.update("boardMapper.updateIncreaseCount", boardNo);
	}
	
	public BoardDto selectBoard(int boardNo) {
		return sqlSessionTemplate.selectOne("boardMapper.selectBoard", boardNo);
	}
	
	public List<ReplyDto> selectReplyList(int boardNo){
		return sqlSessionTemplate.selectList("boardMapper.selectReplyList", boardNo);
	}
	
	public int insertReply(ReplyDto reply) {
		return sqlSessionTemplate.insert("boardMapper.insertReply", reply);
	}
	
	public int deleteReply(int replyNo) {
		return sqlSessionTemplate.update("boardMapper.deleteReply", replyNo);
	}
	
	public List<AttachDto> selectDelFileList(String[] delFileNo){
		return sqlSessionTemplate.selectList("boardMapper.selectDelFileList", delFileNo);
	}
	
	public int updateBoard(BoardDto board) {
		return sqlSessionTemplate.update("boardMapper.updateBoard", board);
	}
	
	public int deleteAttach(String[] delFileNo) {
		return sqlSessionTemplate.delete("boardMapper.deleteAttach", delFileNo);
	}
	
	public int deleteBoard(int boardNo) {
		return sqlSessionTemplate.update("boardMapper.deleteBoard", boardNo);
	}
	
	public int deleteReplyCompletely() {
		return sqlSessionTemplate.delete("boardMapper.deleteReplyCompletely");
	}
	
}
