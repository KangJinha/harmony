package com.harmony.message.model.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
	private String msgNo;
	private String receiveMem;
	private String sendMem;
	private String content;
	private Date sendDate;
	private String readState;
	private catType catCode;
	
	public enum catType{
		NOTICE("NOTICE"),
		MEMBER("MEMBER"),
		LESSON("LESSON"),
		ENSEMBLE("ENSEMBLE");
		
		private String type;
		
		catType(String type){
			this.type = type.toUpperCase();
		}
		
		public String getType() {
			return type;
		}
	}
}

