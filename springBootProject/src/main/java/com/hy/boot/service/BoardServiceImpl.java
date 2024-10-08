package com.hy.boot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hy.boot.dao.BoardDao;
import com.hy.boot.dto.AttachDto;
import com.hy.boot.dto.BoardDto;
import com.hy.boot.dto.PageInfoDto;
import com.hy.boot.dto.ReplyDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardDao boardDao;
	
	@Override
	public int selectBoardListCount() {
		return boardDao.selectBoardListCount();
	}

	@Override
	public List<BoardDto> selectBoardList(PageInfoDto pi) {
		return boardDao.selectBoardList(pi);
	}

	@Override
	public int selectSearchListCount(Map<String, String> search) {
		return boardDao.selectSearchListCount(search);
	}

	@Override
	public List<BoardDto> selectSearchList(Map<String, String> search, PageInfoDto pi) {
		return boardDao.selectSearchList(search, pi);
	}

	@Override
	public int insertBoard(BoardDto board) {
		// board insert
		int result1 = boardDao.insertBoard(board);
		
		int result2 = 1;
		// attachment insert
		List<AttachDto> attachList = board.getAttachList();
		if(!attachList.isEmpty()) {
			result2 = 0;
			for(AttachDto at : attachList) {
				result2 += boardDao.insertAttach(at);
			}
		}
		
		return result1 * result2;
	}

	@Override
	public int updateIncreaseCount(int boardNo) {
		return boardDao.updateIncreaseCount(boardNo);
	}

	@Override
	public BoardDto selectBoard(int boardNo) {
		return boardDao.selectBoard(boardNo);
	}

	@Override
	public List<ReplyDto> selectReplyList(int boardNo) {
		return boardDao.selectReplyList(boardNo);
	}

	@Override
	public int insertReply(ReplyDto reply) {
		return boardDao.insertReply(reply);
	}

	@Override
	public int deleteReply(int replyNo) {
		return boardDao.deleteReply(replyNo);
	}


	@Override
	public List<AttachDto> selectDelFileList(String[] delFileNo) {
		return delFileNo != null ? boardDao.selectDelFileList(delFileNo)
								 : new ArrayList<AttachDto>();
	}

	@Override
	public int updateBoard(BoardDto board, String[] delFileNo) {
		// 게시글 정보 update
		int result1 = boardDao.updateBoard(board);
		
		// 삭제할 첨부파일 정보 delete
		int result2 = delFileNo == null ? 1
										: boardDao.deleteAttach(delFileNo);
		// 새로운 첨부파일 정보 insert
		List<AttachDto> list = board.getAttachList();
		int result3 = 0;
		for(AttachDto at : list) {
			result3 += boardDao.insertAttach(at);
		}
		return result1 == 1
				&& result2 > 0
				&& result3 == list.size()
					? 1 : -1;
	}

	@Override
	public int deleteBoard(int boardNo) {
		return boardDao.deleteBoard(boardNo);
	}

	@Override
	public int deleteReplyCompletely() {
		return boardDao.deleteReplyCompletely();
	}

}
