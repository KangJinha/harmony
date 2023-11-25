package com.harmony.message.controller.ajax;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.harmony.common.exception.HarmonyException;
import com.harmony.message.model.dto.Message;
import com.harmony.message.service.MessageService;
import com.harmony.model.dto.Member;

/**
 * Servlet implementation class MessageListServlet
 */
@WebServlet(name = "messageListServlet",urlPatterns = {"/message/list.do"})
public class MessageListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageListServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Member loginMember = (Member)request.getSession().getAttribute("loginMember");
		if(loginMember == null) {
			throw new HarmonyException("로그인이 필요합니다");
		}
		Gson gson = new Gson();
		JsonObject result = new JsonObject();
		String memno = loginMember.getMemNo();
		List<Message> messageList = MessageService.getService().selectMessageByMemno(memno);
		result.addProperty("messageList", gson.toJson(messageList, List.class));
		
		gson.toJson(result,response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
