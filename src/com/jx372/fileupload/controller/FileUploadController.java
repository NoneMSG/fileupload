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
	
	//file은 무조건 form방식으로
	@RequestMapping( value="/upload", method=RequestMethod.POST )
	public String upload(
			@RequestParam(value="email", required=true, defaultValue="")String email, 
			@RequestParam(value="file1") MultipartFile file1,
			Model model){
		 
		System.out.println(email);
		System.out.println(file1);
		String url1 = fileuploadService.restore(file1);
		model.addAttribute("url1",url1);
		return "result";
	}
}
