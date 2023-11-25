package com.harmony.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.admin.model.dto.NoticeAttachFile;
import com.harmony.admin.service.AdminService;
import com.harmony.common.exception.HarmonyException;

/**
 * Servlet implementation class AdminNoticeDeleteServlet
 */
@WebServlet("/admin/notice/delete.do")
public class AdminNoticeDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminNoticeDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		int noticeNo = -1;
		try {
			noticeNo = Integer.parseInt(no);
			if(!(noticeNo>0)) {
				throw new RuntimeException("");
			}
		}catch(Exception e) {
			throw new HarmonyException("유효하지 않은 공지사항 글번호입니다.");
		}
		String path = getServletContext().getRealPath("/upload/admin/notice");
		List<NoticeAttachFile> delFile = AdminService.getService().deleteNotice(noticeNo);
		if(delFile!=null && delFile.size()>0) {
			for(NoticeAttachFile f : delFile) {
				File deleteFile = new File(path+"/"+f.getReName());
				if(deleteFile.exists())deleteFile.delete();
			}
		}
		if(delFile ==null) {
			throw new HarmonyException("삭제중 오류가 발생하였습니다.");
		}
		response.sendRedirect(request.getContextPath()+"/admin/noticelist.do");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
