package com.hy.boot.dto;

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

public class ReplyDto {

	private int replyNo;
	private String replyContent;
	private int refBoardNo; // 참조게시글 번호
	private String replyWriter; // 댓글작성자 아이디
	private String registDt; // 댓글작성날짜
	private String status;
	
}
