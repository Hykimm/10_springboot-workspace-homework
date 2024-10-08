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
public class MemberDto {

	private int userNo;
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String gender;
	private String phone;
	private String address;
	private String profileURL;
	private Date signupDt;
	private Date modifyDt;
	private String status;
	
}
