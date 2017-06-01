package com.jx372.fileupload.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	//패스값 설정은 servlet에서 설정이 가능하다.
	private static final String SAVE_PATH = "/uploads";
	private static final String PREFIX_URL = "/upload/images/";
	
	
	public String restore(MultipartFile multipartFile) {
		//데이터를 받아오는데 데이터가 없다면 그냥 다른페이지로 가게하고
		String url = "";
		
		try{
			
		if(multipartFile.isEmpty()==true){
			return url;
		}
		//데이터가 있다면 원본의 이름 사이즈 어떤 이름으로 저장할지에 대한 정보 그리고 확장자 를 설정한다.
		String originalFileName
			=multipartFile.getOriginalFilename();
		//확장자 설정으로 어떤 파일들은 .을 여러개 가지고 있기 때문에 확장자는 파일이름의 맨뒤에 붙으니까 뒤에서부터 찾아야한다.
		String extName = originalFileName.substring(
				originalFileName.lastIndexOf('.'),
				originalFileName.length());
		Long fileSize = multipartFile.getSize();
		//파일이름을 어떤식으로 바꿀건지 설정
		String saveFileName = genSaveFileName(extName);
		System.out.println("$$$$$$$$$$$$$$$$$$$"+originalFileName);
		System.out.println("$$$$$$$$$$$$$$$$$$$"+extName);
		System.out.println("$$$$$$$$$$$$$$$$$$$"+saveFileName);
		System.out.println("$$$$$$$$$$$$$$$$$$$"+fileSize);
		//multipartFile에 어떤 이름으로 저장할지에 대해 설정하고 저장하도록 함수에 넘긴다.
		writeFile(multipartFile, saveFileName);
		
		//저장된 파일에 접근할 수 있도록 url을 만들어준다.
		url=PREFIX_URL + saveFileName;
		System.out.println(url);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
		//url을 반환한다.
		return url;
	}
	
	private String genSaveFileName(String extName){
		String fileName = "";
		//일단 확장자가 무엇인지 받은다음에 파일이 업로드된 시간으로 파일이름을 설정한다.
		//파일이름이 중복되는 현상을 방지하기 위함으로 꼭 시간으로 하지 않아도 되고 여러가지 generate기술을 사용하면 된다
		Calendar calendar = Calendar.getInstance();
		fileName+=calendar.get(Calendar.YEAR);
		fileName+=calendar.get(Calendar.MONTH);
		fileName+=calendar.get(Calendar.DATE);
		fileName+=calendar.get(Calendar.HOUR);
		fileName+=calendar.get(Calendar.MINUTE);
		fileName+=calendar.get(Calendar.SECOND);
		fileName+=calendar.get(Calendar.MILLISECOND);
		fileName+=extName; //마지막엔 확장자를 붙여준다.
		
		return fileName;
	}
	
	private void writeFile(MultipartFile multipartFile, String saveFileName) throws IOException{
		byte[] fileData = multipartFile.getBytes();
		//데이터를 진짜 저장하는 로직으로 바이트 단위로 데이터를 저장한다.
		//서버쪽에 저장하고 싶은 path에 파일을 저장시키는 설정을 하고 저장시킨다.
		FileOutputStream fos = new FileOutputStream(SAVE_PATH+"/"+saveFileName);
		fos.write(fileData);
		fos.close();
	}
}
