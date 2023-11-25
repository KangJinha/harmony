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
import com.harmony.board.info.model.dto.InfoBoard;
import com.harmony.board.model.service.InfoBoardService;
import com.harmony.model.dto.Member;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class InfoBoardWriteEndServlet
 */
@WebServlet("/board/boardWriteEnd.do")
public class InfoBoardWriteEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoBoardWriteEndServlet() {
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
			 
			InfoBoard b=InfoBoard.builder()
		     	.infBrdWriter(memNo)
			    .infBrdTitle(mr.getParameter("title"))  
			    .infBrdContent(mr.getParameter("bo_content"))  
			    .infBrdTitleImg(mr.getFilesystemName("upfile"))  
			    .infBrdRegion(mr.getParameter("region"))  
			    .infBrdCatNo(mr.getParameter("category"))  
			    .infBrdTagNo(mr.getParameter("tag"))  
			    .build();
			
			int result=new InfoBoardService().insertBoard(b);
			String msg,loc;
			if(result>0) {
			    msg="게시글 등록성공";
			    loc="/infoBoardList.do";
			}else {
			    msg="게시글 등록실패";
			    loc="/board/boardWrite.do";
			    File delFile=new File(path+b.getInfBrdTitleImg()); 
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
