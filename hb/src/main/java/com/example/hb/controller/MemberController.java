package com.example.hb.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.hb.dao.MemberDao;
import com.example.hb.dao.ProfileDao;
import com.example.hb.dto.EmailDto;
import com.example.hb.dto.IdDto;
import com.example.hb.dto.MemberDto;
import com.example.hb.dto.ProfileDto;
import com.example.hb.service.S3Uploader;
import com.google.gson.Gson;

@RestController

public class MemberController {
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private ProfileDao profileDao;
	
	@Autowired
	private S3Uploader s3Uploader;
	
	@GetMapping("/members")
	public List<MemberDto> members() throws Exception {
		final List<MemberDto> memberList = memberDao.selectMembers();
		return memberList;
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public int signUp(@RequestBody String objJson) throws Exception{
		int result;
		Gson gson = new Gson();
		MemberDto dto = gson.fromJson(objJson, MemberDto.class);
		result=memberDao.signUp(dto.getmId(), dto.getmPwd(), dto.getmEmail());
		return result;		
	}
	
	@RequestMapping(value="/idcheck", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public int idCheck(@RequestBody String objJson) throws Exception{
		int result;
		Gson gson = new Gson();
		IdDto dto = gson.fromJson(objJson, IdDto.class);
		result=memberDao.idCheck(dto.getmId());
		return result;
	}
	
	@RequestMapping(value="/create_profile", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public int createProfile(@RequestParam("pId") String pId, 
							@RequestParam("pNickname") String pNickname,
							@RequestParam("pGym") String pGym,
							@RequestParam("pAge") String pAge,
							@RequestParam("pHeight") String pHeight,
							@RequestParam("pWeight") String pWeight,
							@RequestParam("pSex") String pSex,
							@RequestParam("pRoutine") String pRoutine,
							@RequestParam("pDetail") String pDetail,
							@RequestParam("pOpen") String pOpen,
							@RequestPart(value="pImg", required=false) MultipartFile file) throws Exception{
		int result;
		String pImg = file.getOriginalFilename();
		s3Uploader.upload(file, "test");
		result=profileDao.create(pId, pNickname, pGym, pAge, pHeight, pWeight, pSex, pRoutine, pDetail, pImg, pOpen);
		return result;
	}
	
	@RequestMapping(value="/emailcheck", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public int emailCheck(@RequestBody String objJson) throws Exception{
		int result;
		Gson gson = new Gson();
		EmailDto dto = gson.fromJson(objJson, EmailDto.class);
		result=memberDao.emailCheck(dto.getmEmail());
		return result;
	}
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	public String exceptionHandler(IllegalArgumentException ex) {
		return ex.getMessage();
	}
}
