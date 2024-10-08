package com.hy.boot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hy.boot.dao.NoticeDao;
import com.hy.boot.dto.AttachDto;
import com.hy.boot.dto.NoticeDto;
import com.hy.boot.dto.PageInfoDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

	private final NoticeDao noticeDao;

	@Override
	public int selectNoticeListCount() {
		return noticeDao.selectNoticeListCount();
	}

	@Override
	public List<NoticeDto> selectNoticeList(PageInfoDto pi) {
		return noticeDao.selectNoticeList(pi);
	}

	@Override
	public int insertNotice(NoticeDto notice) {
		int result1 = noticeDao.insertNotice(notice);
		int result2 = 1;
		List<AttachDto> attachList = notice.getAttachList();
		if(!attachList.isEmpty()) {
			result2 = 0;
			for(AttachDto at : attachList) {
				result2 += noticeDao.insertAttach(at);
			}
		}
		return result1 * result2;
	}

	@Override
	public NoticeDto selectNotice(int no) {
		return noticeDao.selectNotice(no);
	}
	
}
