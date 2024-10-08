package com.hy.boot.dto;

import java.sql.Date;

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
public class AttachDto {

	private int fileNo;
	private String filePath;
	private String filesystemName;
	private String originalName;
	private Date uploadDt;
	private String refType; //참조게시글유형(공지사항N | 일반게시글B)
	private int refNo; //참조게시글번호
	
}
