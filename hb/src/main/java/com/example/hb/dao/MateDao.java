package com.example.hb.dao;

import org.apache.ibatis.annotations.Param;

public interface MateDao {
	int create(@Param("mId")String mId, @Param("mtId")String mtId);
	int accept(@Param("mId")String mId, @Param("mtId")String mtId);
	Integer list(@Param("mId")String mId, @Param("mtId")String mtId);
	int update(@Param("mId")String mId);
}
