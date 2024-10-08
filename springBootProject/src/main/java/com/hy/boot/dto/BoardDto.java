package com.hy.boot.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class BoardDto {

	private int boardNo;
	private String boardTitle;
	private String boardWriter;
	private String boardContent;
	private int count;
	private String registDt;
	private String status;
	private int attachCount; // 첨부파일의 갯수
	
	// has many 관계
	// attachDto 객체를 여러개 담을 수 있는 리스트
	private List<AttachDto> attachList;
	
}
