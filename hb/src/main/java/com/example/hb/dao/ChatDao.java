package com.example.hb.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hb.dto.ChatDto;
import com.example.hb.dto.MessageDto;

public interface ChatDao {
	List<ChatDto> list(@Param("crId")String crId, @Param("pId")String pId,@Param("crId2")String crId2);
	List<ChatDto> listAll(@Param("pId")String pId,@Param("crId")String crId,@Param("crId2")String crId2,@Param("gym")String gym);
	List<MessageDto> list2(@Param("crId")String crId, @Param("mgSender")String mgSender);
	String gym(@Param("pId")String pId);
	int create(@Param("pId")String pId, @Param("crId")String crId);
	int create2(@Param("pId2")String pId2, @Param("crId")String crId);
	int send(@Param("mgSender") String mgSender,@Param("mgDetail") String mgDetail,	@Param("chroom") String chroom);
}
 