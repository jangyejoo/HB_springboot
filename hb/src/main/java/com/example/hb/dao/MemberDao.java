package com.example.hb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.hb.dto.LoginDto;
import com.example.hb.dto.MemberDto;

public interface MemberDao {
	List<MemberDto> selectMembers();
	int signUp(@Param("mId")String mId, @Param("mPwd")String mPwd, @Param("mEmail")String mEmail);
	int idCheck(@Param("mId")String mId);
	LoginDto authenticate(@Param("mId")String mId);
}
