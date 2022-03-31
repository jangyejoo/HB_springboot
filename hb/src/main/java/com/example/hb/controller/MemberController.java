package com.example.hb.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import com.example.hb.dao.ChatDao;
import com.example.hb.dao.MateDao;
import com.example.hb.dao.MemberDao;
import com.example.hb.dao.ProfileDao;
import com.example.hb.dto.ChatDto;
import com.example.hb.dto.EmailDto;
import com.example.hb.dto.IdDto;
import com.example.hb.dto.MemberDto;
import com.example.hb.dto.MessageDto;
import com.example.hb.dto.ProfileDto;
import com.example.hb.dto.PwdDto;
import com.example.hb.service.S3Uploader;
import com.google.gson.Gson;

@RestController

public class MemberController {
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private ProfileDao profileDao;
	
	@Autowired
	private ChatDao chatDao;
	
	@Autowired
	private MateDao mateDao;
	
	@Autowired
	private S3Uploader s3Uploader;
	
	@GetMapping("/member")
	public List<MemberDto> member() throws Exception {
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
	
	@RequestMapping(value="/update_password", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public int updatePwd(@RequestBody String objJson) throws Exception{
		int result=0;
		String pwd;
		Gson gson = new Gson();
		PwdDto dto = gson.fromJson(objJson, PwdDto.class);
		pwd = memberDao.getPwd(dto.getmId());
		System.out.println("pwd : "+pwd);
		if(pwd.equals(dto.getmPwd())) {
			System.out.println("일치");
			result = memberDao.updatePwd(dto.getmId(),dto.getnewPwd());
		}
		return result;
		
	}
	
	@RequestMapping(value="/withdrawal", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public int withdraw(@RequestBody String objJson) throws Exception{
		int result;
		Gson gson = new Gson();
		IdDto dto = gson.fromJson(objJson, IdDto.class);
		result = memberDao.withdrawProfile(dto.getmId());
		result *= memberDao.withdrawMember(dto.getmId());
		String result1;
		String result2;
		result1=mateDao.select1(dto.getmId());
		result2=mateDao.select2(dto.getmId());
		if(result1!=null) {
			mateDao.update2(result1);
		}
		if(result2!=null) {
			mateDao.update2(result2);
		}
		memberDao.deleteMate(dto.getmId());
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
	
	@RequestMapping(value="/profilecheck", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public int profileCheck(@RequestBody String objJson) throws Exception{
		int result;
		Gson gson = new Gson();
		IdDto dto = gson.fromJson(objJson, IdDto.class);
		result=profileDao.profileCheck(dto.getmId());
		return result;
	}
	
	@RequestMapping(value="/profile", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public ProfileDto profile(@RequestParam("pId") String pId) throws Exception{
		return profileDao.profile(pId);
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
		//String pImg = file.getOriginalFilename();
		String url=s3Uploader.upload(file, "test");
		result=profileDao.create(pId, pNickname, pGym, pAge, pHeight, pWeight, pSex, pRoutine, pDetail, url, pOpen);
		return result;
	}
	
	@RequestMapping(value="/update_profile", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public int updateProfile(@RequestParam("pId") String pId, 
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
		//String pImg = file.getOriginalFilename();
		if(file==null) {
			System.out.println("파일이 없습니다");
			result=profileDao.update2(pId, pNickname, pGym, pAge, pHeight, pWeight, pSex, pRoutine, pDetail, pOpen);
		} else {
			System.out.println("파일이 있습니다");
			String url=s3Uploader.upload(file, "test");
			result=profileDao.update(pId, pNickname, pGym, pAge, pHeight, pWeight, pSex, pRoutine, pDetail, url, pOpen);
		}
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
	
	@RequestMapping(value="/members", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public List<ProfileDto> members(@RequestParam("pId") String pId) throws Exception {
		List<ProfileDto> memberList = profileDao.members(pId);
		return memberList;
	}
	
	@RequestMapping(value="/chat", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public List<ChatDto> list(@RequestParam("crId") String crId, 
			@RequestParam("pId2") String pId2,
			@RequestParam("pId") String pId ) throws Exception {
		String crId2 = pId2+"+"+pId;
		List<ChatDto> chatList = chatDao.list(crId,pId,crId2);
		return chatList;
	}
	
	@RequestMapping(value="/chatAll", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public List<ChatDto> listAll(@RequestParam("pId") String pId ) throws Exception {
		String crId = "%+"+pId;
		String crId2 = pId+"+%";
		String gym=String.valueOf(chatDao.gym(pId));
		System.out.println(gym);
		List<ChatDto> chatListAll = chatDao.listAll(pId, crId, crId2, gym);
		return chatListAll;
	}
	
	@RequestMapping(value="/create_chat", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public int createChat (@RequestParam("pId") String pId,
			@RequestParam("pId2") String pId2,
			@RequestParam("crId") String crId) throws Exception{
		int result1;
		int result2;
		result1=chatDao.create(pId, crId);
		result2=chatDao.create(pId2, crId);
		return result1+result2;
	}
	
	@RequestMapping(value="/msg", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public List<MessageDto> list(@RequestParam("crId") String crId,
			@RequestParam("mgSender") String mgSender ) throws Exception {
		List<MessageDto> chatList = chatDao.list2(crId,mgSender);
		return chatList;
	}
	
	@RequestMapping(value="/create_msg", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public int createMsg (@RequestParam("mgSender") String mgSender,
			@RequestParam("mgDetail") String mgDetail,
			@RequestParam("chroom") String chroom) throws Exception{
		int result;
		result=chatDao.send(mgSender, mgDetail, chroom);
		return result;
	}
	
	@RequestMapping(value="/create_mate", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public int createMate(@RequestParam("mId") String mId,
			@RequestParam("mtId") String mtId ) throws Exception {
		int result;
		result = mateDao.create(mId,mtId);
		return result;
	}
	
	@RequestMapping(value="/mate", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public Integer checkMate(@RequestParam("mId") String mId,
			@RequestParam("mtId") String mtId ) throws Exception {
		Integer result;
		result=mateDao.list(mId,mtId);
		System.out.println(result);
		return result;
	}
	
	@RequestMapping(value="/accept_mate", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public int acceptMate(@RequestParam("mId") String mId,
			@RequestParam("mtId") String mtId ) throws Exception {
		int result;
		result = mateDao.accept(mId,mtId);
		mateDao.update(mId);
		mateDao.update(mtId);
		return result;
	}
	
	@RequestMapping(value="/delete_mate", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public int deleteMate(@RequestParam("mId") String mId,
			@RequestParam("mtId") String mtId) throws Exception {
		int result;
		result=mateDao.delete(mId, mtId);
		mateDao.update2(mId);
		mateDao.update2(mtId);
		return result;
	}
	
	@RequestMapping(value="/buddy", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public ProfileDto buddy(@RequestParam("pId") String pId) throws Exception {
		String result1;
		String result2;
		result1=mateDao.select1(pId);
		result2=mateDao.select2(pId);
		if(result1!=null) {
			return mateDao.buddy(result1);
		}
		if(result2!=null) {
			return mateDao.buddy(result2);
		}
		return null;
	}
	
	@ExceptionHandler(value = IllegalArgumentException.class)
	public String exceptionHandler(IllegalArgumentException ex) {
		return ex.getMessage();
	}
}
