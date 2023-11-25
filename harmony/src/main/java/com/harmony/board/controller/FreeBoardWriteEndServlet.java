package com.harmony.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.harmony.board.exception.BadAccessException;
import com.harmony.board.free.model.dto.FreeBoard;
import com.harmony.board.model.service.FreeBoardService;
import com.harmony.model.dto.Member;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class FreeBoardWriteEndServlet
 */
@WebServlet("/board/freeboardWriteEnd.do")
public class FreeBoardWriteEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FreeBoardWriteEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!ServletFileUpload.isMultipartContent(request)) {
			throw new BadAccessException("잘못된 접근입니다. 관리자에게 문의하세요!");
		}else {
			String path=getServletContext().getRealPath("/upload/");
			path+="board";
			File dir=new File(path);
			if(!dir.exists()) dir.mkdirs();
			MultipartRequest mr=new MultipartRequest(
					request, path,1024*1024*100,"UTF-8",
					new DefaultFileRenamePolicy());
			
			Member loginMember = (Member) request.getSession().getAttribute("loginMember");
			 String memNo = loginMember.getMemNo();
			
			FreeBoard b = FreeBoard.builder()
					.freBrdWriter(memNo)
					/* .freBrdWriter(mr.getParameter("freBrdWriter")) */
				    .freBrdTitle(mr.getParameter("title"))  
				    .freBrdContent(mr.getParameter("bo_content"))  
				    .freBrdTitleImg(mr.getFilesystemName("upfile"))  
				    .build();

				int result = new FreeBoardService().insertFreeBoard(b);

			String msg,loc;
			if(result>0) {
			    msg="게시글 등록성공";
			    loc="/freeBoardList.do";
			}else {
			    msg="게시글 등록실패";
			    loc="/board/freeboardWrite.do";
			    File delFile=new File(path+b.getFreBrdTitleImg()); 
			    if(delFile.exists()) delFile.delete();
			}
			request.setAttribute("msg", msg);
			request.setAttribute("loc", loc);
			
			request.getRequestDispatcher("/views/board/communitymsg.jsp")
			.forward(request, response);
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
