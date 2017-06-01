package com.jx372.fileupload.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jx372.fileupload.service.FileUploadService;

@Controller
public class FileUploadController {
	
	@Autowired
	private FileUploadService fileuploadService;
	
	@RequestMapping("/form")
	public String form(){
		return "form";
	}
	
	//file은 무조건 form방식으로 데이터가 길기때문에
	@RequestMapping( value="/upload", method=RequestMethod.POST )
	public String upload(
			//parameter로 데이터를 받아온다. file데이터는 MutipartFile 객체로 받아올 수 있다.
			@RequestParam(value="email", required=true, defaultValue="")String email, 
			@RequestParam(value="file1") MultipartFile file1,
			Model model){
		 //MultipartFile에 데이터를 받으면 자동적으로 파일의 속성값들이 객체안에 있는 멤버 변수 값들이 지정된다.
		//그리고 자동적으로 파일이름을 바꿔주게 되는데 원래의 파일이름과 크기 같은 정보가 들어있다.
		System.out.println(email);
		System.out.println(file1);
		//서비스에 파일을 넘겨서 데이터 처리를 할 수 있도록 해준다.
		String url1 = fileuploadService.restore(file1);
		//이제 저장된 url을 브라우저로 넘긴다.
		model.addAttribute("url1",url1);
		return "result";
	}
}
