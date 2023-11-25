package com.harmony.message.controller.ajax;

import java.io.IOException;

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

/**
 * Servlet implementation class MessageServlet
 */
@WebServlet("/message/message.do")
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int messageNo = -1;
		try {
			messageNo = Integer.parseInt(request.getParameter("no"));
			if(messageNo<=0) {
				throw new RuntimeException();
			}
		}catch(Exception e) {
			throw new HarmonyException("유효하지 않은 메세지번호입니다.");
		}
		Gson gson = new Gson();
		JsonObject json = new JsonObject();
		Message message = MessageService.getService().selectMessageByMessageNo(messageNo);
		
		json.addProperty("message", gson.toJson(message,Message.class));
		
		gson.toJson(json,response.getWriter());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
