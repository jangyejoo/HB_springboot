package com.example.hb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.hb.dto.ProfileDto;

public interface MateDao {
	int create(@Param("mId")String mId, @Param("mtId")String mtId);
	int accept(@Param("mId")String mId, @Param("mtId")String mtId);
	Integer list(@Param("mId")String mId, @Param("mtId")String mtId);
	int update(@Param("mId")String mId);
	String select1(@Param("pId")String pId);
	String select2(@Param("pId")String pId);
	ProfileDto buddy(@Param("pId")String pId);
	int delete(@Param("mId")String mId,@Param("mtId")String mtId);
	int update2(@Param("mId")String mId);
}
