package com.hy.boot.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {
	public Map<String, String> fileUpload(MultipartFile uploadFile, String folderName){
		
		// 전달된 파일 업로드 처리
		// 1) 업로드시킬 폴더 (외부경로 : /upload/profile|board/yyyy/MM/dd)
		String filePath = "/upload/" + folderName + new SimpleDateFormat("/yyyy/MM/dd").format(new Date());
		File filePathDir = new File("C:" + filePath);
		if(!filePathDir.exists()) { // 해당폴더가 존재하지 않을 경우
			filePathDir.mkdirs(); // 해당폴더가 만들어지도록 
		}
		
		/******************************************************************************************/
		
		// 2) 파일명 수정 작업 (랜덤값.기존확장자)
		String originalName = uploadFile.getOriginalFilename();
		String ext = originalName.endsWith(".tar.gz") ? ".tar.gz" : originalName.substring(originalName.lastIndexOf("."));
		String filesystemName = UUID.randomUUID().toString().replace("-", "") + ext;	
		
		/******************************************************************************************/
		
		// 3) 전달된 첨부파일을 1번과정의 폴더에 2번과정의 파일명으로 변환처리해서 업로드
		try {
			uploadFile.transferTo(new File(filePathDir, filesystemName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		/******************************************************************************************/	
		
		// 4) DB에 데이터 기록시 필요한 데이터 다시 반환해주기 (Map에 담아서)
		Map<String, String> map = new HashMap<>();
		map.put("filePath", filePath);
		map.put("originalName", originalName);
		map.put("filesystemName", filesystemName);
		
		return map;
	}
}
