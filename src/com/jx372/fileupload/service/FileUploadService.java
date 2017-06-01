package com.jx372.fileupload.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	
	private static final String SAVE_PATH = "/uploads";
	private static final String PREFIX_URL = "/upload-images/";
	
	
	public String restore(MultipartFile multipartFile) {
		
		String url = "";
		
		try{
			
		if(multipartFile.isEmpty()==true){
			return url;
		}
		String originalFileName
			=multipartFile.getOriginalFilename();
		String extName = originalFileName.substring(
				originalFileName.lastIndexOf('.'),
				originalFileName.length());
		Long fileSize = multipartFile.getSize();
		String saveFileName = genSaveFileName(extName);
		System.out.println("$$$$$$$$$$$$$$$$$$$"+originalFileName);
		System.out.println("$$$$$$$$$$$$$$$$$$$"+extName);
		System.out.println("$$$$$$$$$$$$$$$$$$$"+saveFileName);
		System.out.println("$$$$$$$$$$$$$$$$$$$"+fileSize);
		
		writeFile(multipartFile, saveFileName);
		
		url=PREFIX_URL + saveFileName;
		System.out.println(url);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
		return url;
	}
	
	private String genSaveFileName(String extName){
		String fileName = "";
		
		Calendar calendar = Calendar.getInstance();
		fileName+=calendar.get(Calendar.YEAR);
		fileName+=calendar.get(Calendar.MONTH);
		fileName+=calendar.get(Calendar.DATE);
		fileName+=calendar.get(Calendar.HOUR);
		fileName+=calendar.get(Calendar.MINUTE);
		fileName+=calendar.get(Calendar.SECOND);
		fileName+=calendar.get(Calendar.MILLISECOND);
		fileName+=extName;
		
		return fileName;
	}
	
	private void writeFile(MultipartFile multipartFile, String saveFileName) throws IOException{
		byte[] fileData = multipartFile.getBytes();
		
		FileOutputStream fos = new FileOutputStream(SAVE_PATH+"/"+saveFileName);
		fos.write(fileData);
		fos.close();
	}
}
