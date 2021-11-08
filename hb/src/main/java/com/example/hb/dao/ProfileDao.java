package com.example.hb.dao;

import org.apache.ibatis.annotations.Param;

public interface ProfileDao {
	//int create(@Param("pId")String pId, @Param("pNickname")String pNickname, @Param("pGym")String pGym, @Param("pAge")String pAge, @Param("pHeight")String pHeight, @Param("pWeight")String pWeight, @Param("pSex")int pSex, @Param("pRoutine")String pRoutine,@Param("pDetail")String pDetail,@Param("pImg")String pImg, @Param("pOpen")int pOpen);
	int image(@Param("pImg")String pImg, @Param("pId")String pId);
	int create(@Param("pId")String pId, @Param("pNickname")String pNickname, @Param("pGym")String pGym, @Param("pAge")String pAge, @Param("pHeight")String pHeight, @Param("pWeight")String pWeight, @Param("pSex")String pSex, @Param("pRoutine")String pRoutine,@Param("pDetail")String pDetail,@Param("pImg")String pImg,@Param("pOpen")String pOpen);
}
