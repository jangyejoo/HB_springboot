package com.example.hb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.hb.dto.MemberDto;
import com.example.hb.dto.ProfileDto;

public interface ProfileDao {
	int create(@Param("pId")String pId, @Param("pNickname")String pNickname, @Param("pGym")String pGym, @Param("pAge")String pAge, @Param("pHeight")String pHeight, @Param("pWeight")String pWeight, @Param("pSex")String pSex, @Param("pRoutine")String pRoutine,@Param("pDetail")String pDetail,@Param("pImg")String pImg,@Param("pOpen")String pOpen);
	int update(@Param("pId")String pId, @Param("pNickname")String pNickname, @Param("pGym")String pGym, @Param("pAge")String pAge, @Param("pHeight")String pHeight, @Param("pWeight")String pWeight, @Param("pSex")String pSex, @Param("pRoutine")String pRoutine,@Param("pDetail")String pDetail,@Param("pImg")String pImg,@Param("pOpen")String pOpen);
	int update2(@Param("pId")String pId, @Param("pNickname")String pNickname, @Param("pGym")String pGym, @Param("pAge")String pAge, @Param("pHeight")String pHeight, @Param("pWeight")String pWeight, @Param("pSex")String pSex, @Param("pRoutine")String pRoutine,@Param("pDetail")String pDetail,@Param("pOpen")String pOpen);
	int image(@Param("pImg")String pImg, @Param("pId")String pId);
	List<ProfileDto> members(@Param("pId")String pId);
	int profileCheck(@Param("pId")String pId);
	ProfileDto profile(@Param("pId")String pId);
}
