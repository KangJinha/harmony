package com.harmony.message.controller;

import com.harmony.message.model.dto.Message;
import com.harmony.message.service.MessageService;

public class SendMessage {
	public static boolean sendMessage(Message message) {
		int result = MessageService.getService().sendMessage(message);
		if(result>0) {
			return true;
		}else {
			return false;
		}
	}
	
}
