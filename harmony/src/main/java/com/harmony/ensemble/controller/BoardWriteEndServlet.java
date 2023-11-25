package com.harmony.ensemble.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.harmony.ensemble.model.dto.EnsembleBoard;
import com.harmony.ensemble.model.dto.EnsembleBoardWantPart;
import com.harmony.ensemble.model.service.EnsembleService;
import com.harmony.model.dto.Member;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;


/**
 * Servlet implementation class BoardWriteEndServlet
 */
@WebServlet("/ensemble/boardWriteEnd.do")
public class BoardWriteEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardWriteEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		EnsembleService es = new EnsembleService();
		
		if(!ServletFileUpload.isMultipartContent(request)) {
			throw new IllegalArgumentException("BoardWriteEndServlet FileUpload Error");
			
		}else {
				String path=getServletContext().getRealPath("/upload/ensemble/");
				
				MultipartRequest mr = new MultipartRequest(request, 
															path, 1024*1024*200, "UTF-8", new DefaultFileRenamePolicy());
		
				String location = mr.getParameter("location");
				
				String parts = mr.getParameter("parts"); 
				String[] partsArray = parts.split(",");
				
				String title = mr.getParameter("title");
				
				String detail = mr.getParameter("detail");
				
				String ensBoardNo = es.selectBoardSeq();
				
				List<EnsembleBoardWantPart> partList = new ArrayList<>();
				
				
				for(String part : partsArray) {
					partList.add(EnsembleBoardWantPart.builder()
										.ensInstNo(part)		
										.ensBoardNo(ensBoardNo)
										.build()
								);
				}
				
				//로그인한 멤버의 회원번호와 일치하는 회원의 팀 번호 가져오기.
				Member loginMember = (Member)request.getSession().getAttribute("loginMember");
				String loginMemNo = loginMember.getMemNo();
				String teamNo = es.selectTeamNoByMemNo(loginMemNo);
				
				EnsembleBoard board = EnsembleBoard.builder()
										.ensBoardNo(ensBoardNo)
										.ensTeamNo(teamNo)
										.ensWriter(loginMemNo)
										.ensLocation(mr.getParameter("location"))
										.ensPlace(mr.getParameter("place"))
										.ensDetail(mr.getParameter("detail"))
										.ensBoardTitle(mr.getParameter("title"))
										.build();
				
				int result = es.insertEnsBoard(board, partList);
				
				if(result<0) System.out.println("글 등록 실패");
				else System.out.println("글 등록 성공");
		}
	
	
		//에이작스
//		String data=request.getParameter("data");
//		String part=request.getParameter("part");
//		System.out.println("data: " + data);
//		System.out.println("part: " + part);
//		//json -> 전송된 데이터를 객체로 변환
//		Gson gson = new Gson();
//		EnsembleBoard board  = gson.fromJson(data, EnsembleBoard.class);
//		System.out.println("보드" + board);
//		List<String> parts = (List<String>)gson.fromJson(part, List.class);
		
		
//		System.out.println(": 여기: " + parts);
		
		
//		String location = request.getParameter("location");
//		String parts = request.getParameter("parts");
		
		
		//로그인한 멤버의 회원번호와 일치하는 회원의 팀 번호 가져오기.
//				Member loginMember = (Member)request.getSession().getAttribute("loginMember");
//				String loginMemNo = loginMember.getMemNo();
//				
//				String teamNo = es.selectTeamNoByMemNo(loginMemNo);
		

		

//		EnsembleTeam ensTeam = es.writeBoard(board, parts); 
		//모집 글,모집파트(List) : insert
		//합주 팀 : select
											
		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
