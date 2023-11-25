package com.harmony.member.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.harmony.board.exception.BadAccessException;
import com.harmony.member.service.MemberService;
import com.harmony.model.dto.MemberInfo;
import com.harmony.model.dto.MemberMusic;
import com.harmony.model.dto.MemberVideo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class AddIntroduceServlet
 */
@WebServlet("/member/addIntroduceServlet.do")
public class AddIntroduceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddIntroduceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(!ServletFileUpload.isMultipartContent(request)){
			throw new BadAccessException("잘못된접근입니다. 관리자에게 문의하세요");
		}else {
			String path= getServletContext().getRealPath("/upload/");
			int maxSize=1024*1024*300;//300mb
			String encoding ="UTF-8";
			DefaultFileRenamePolicy dfr = new DefaultFileRenamePolicy();
			MultipartRequest mr = new MultipartRequest(request,path,maxSize,encoding,dfr);
		String memNo =mr.getParameter("memNo");
		String email =mr.getParameter("email");
		String profilephoto = mr.getParameter("profilephoto");
		String name = mr.getParameter("name");
		int age = Integer.parseInt(mr.getParameter("age"));
		String school = mr.getParameter("school");
		String department= mr.getParameter("department");
		String schoolstate=mr.getParameter("schoolstate");
		String gender =mr.getParameter("gender");
		String activityarea = mr.getParameter("activityarea");
		String genre[] = mr.getParameterValues("genre");
		String interest[] = mr.getParameterValues("interest");
		String introduce = mr.getParameter("introduce");
		String videolink = mr.getParameter("videolink");
		String musiclink = mr.getParameter("musiclink");
		String memCareer = mr.getParameter("memcareer");
		System.out.println(memNo);
		System.out.println(gender);
		System.out.println(name);
			List<MemberVideo> memberVideo =new ArrayList<>();
			List<MemberMusic> memberMusic =new ArrayList<>();
			
			Enumeration<String> filenames= mr.getFileNames();
		while(filenames.hasMoreElements()) {
			
			String filename=filenames.nextElement();
			String rename=mr.getFilesystemName(filename);
			String oriname=mr.getOriginalFileName(filename);
			String filetype=filename.substring(0,5);
			File file = mr.getFile(filename);
			if(filetype.equals("video")){
			memberVideo.add(MemberVideo.builder().memNo(memNo).videoType("FILE").videoLink(rename).build());
			memberVideo.add(MemberVideo.builder().memNo(memNo).videoType("LINK").videoLink(videolink).build());
			
			}else if(filetype.equals("music")) {
			memberMusic.add(MemberMusic.builder().memNo(memNo).musicType("FILE").musicLink(rename).build());
			memberMusic.add(MemberMusic.builder().memNo(memNo).musicType("LINK").musicLink(musiclink).build());
			}else if(filetype.equals("profi")) {
				profilephoto=rename;
				
			}
		}			
		MemberInfo mi= MemberInfo.builder()
						    .memNo(memNo)
							.activityArea(activityarea)
							.introduce(introduce)
							.profilPhoto(profilephoto)
							.school(school)
							.department(department)
							.schoolState(schoolstate)
							.name(name)
							.gender(gender)
							.age(age)
							.email(email)
							.interest(interest)
							.genre(genre)
							.memCareer(memCareer)
							.memberVideo(memberVideo)
							.memberMusic(memberMusic)
							.build();
	
		 int result = new MemberService().addintroduce(mi);
		 if(result>0) {
//			 session.setAttribute("member", mi);
			 System.out.println("나의확인용"+mi);
//			 request.getRequestDispatcher("/main.do").forward(request, response);
				response.sendRedirect(request.getContextPath()+"/main.do"); 
		 }
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
