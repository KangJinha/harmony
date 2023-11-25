package com.harmony.ensemble.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.harmony.ensemble.model.dto.EnsembleMember;
import com.harmony.ensemble.model.dto.EnsembleTeam;
import com.harmony.ensemble.model.dto.EnsembleTeamMusic;
import com.harmony.ensemble.model.dto.EnsembleTeamTime;
import com.harmony.ensemble.model.dto.EnsembleTeamVideo;
import com.harmony.ensemble.model.service.EnsembleService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class EnrollTeamEndServlet
 */
@WebServlet("/ensemble/enrollTeamEnd.do")
public class EnrollTeamEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnrollTeamEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 
		
		if(!ServletFileUpload.isMultipartContent(request)) {
			throw new IllegalArgumentException("노!");
			
		}else {
				String path=getServletContext().getRealPath("/upload/ensemble/");
				
				MultipartRequest mr = new MultipartRequest(request, 
															path, 1024*1024*1024, "UTF-8", new DefaultFileRenamePolicy());
				
			Enumeration names = mr.getFileNames();
//			List<Map<String,String>> files = new ArrayList<>();
			List<EnsembleTeamMusic> musicList = new ArrayList<>();
			List<EnsembleTeamVideo> videoList = new ArrayList<>();
			List<EnsembleTeamTime> timeList = new ArrayList<>();
			List<EnsembleMember> ensMemList = new ArrayList<>();
			
			String msg ="";
			
			EnsembleService es = new EnsembleService();
			String ensTeamNo = es.selectSeq();
			System.out.println(ensTeamNo);
	
			
			
			String userEmail = request.getParameter("keyword");
			
			//검색한 이메일의 회원넘버
			String memNo = mr.getParameter("memNo");
			
			
//			System.out.println("userEmail: " + userEmail);
//			System.out.println("memNo" + memNo);
//			System.out.println("memNo2" + memNo2);
//			System.out.println("원인찾기중 EnrollTeamEndServlet ensTeam빌더 위!");
				//문제 : EnsembleMember테이블의 ENS_MEM_NO이 안들어감. (팀멤버 테이블의 회원넘버)
				//검색한 이메일의 회원 넘버 -> 팀멤버 테이블에 회원 넘버로 저장 -> 팀 등록시 팀번호 생성
			
			EnsembleTeam ensTeam = EnsembleTeam.builder()
					.ensTeamNo(ensTeamNo)
					.ensTeamName(mr.getParameter("teamName"))
					.ensTeamInfo(mr.getParameter("detail"))
					.ensGenreNo(mr.getParameter("genre"))
					.ensTeamType(mr.getParameter("type"))
					.build();
			
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
	         Date date = new Date(System.currentTimeMillis());
	         
	         Timestamp startTime = Timestamp.valueOf(formatter.format(date) +" "+ mr.getParameter("startTime"));
	         Timestamp endTime = Timestamp.valueOf(formatter.format(date) +" "+mr.getParameter("endTime"));
			
	         System.out.println(startTime);
			
	         timeList.add(
		         EnsembleTeamTime.builder()
						.ensTeamNo(ensTeamNo)
						.ensDayOfWeek(mr.getParameter("dayOfWeek"))
						.ensStartTime(startTime)
						.ensEndTime(endTime)
						.build());
			
			while(names.hasMoreElements()) {
				String name = (String)names.nextElement();
				String re = mr.getFilesystemName(name);
				String ori = mr.getOriginalFileName(name);
				
				
				int index=ori.lastIndexOf(".");
				if(index>0) {
					String extension = ori.substring(index +1);
					if(extension.equals("mp3")) {
						musicList.add(EnsembleTeamMusic.builder()
													.teamNo(ensTeamNo)
													.mOriName(mr.getOriginalFileName(name))
													.mReName(mr.getFilesystemName(name))
													.build());
						}else if(extension.equals("mp4")) {
							videoList.add(EnsembleTeamVideo.builder()
														.teamNo(ensTeamNo)
														.vOriName(mr.getOriginalFileName(name))
														.vReName(mr.getFilesystemName(name))
														.build());
						}else {
							msg= "잘못된 파일입니다.";
							System.out.println(msg);
						}
				}
			
				
			}
		
			System.out.println("EnrollTeamEndServlet EnsembleMember builder 위  memNo: "+ memNo );
			ensMemList.add(EnsembleMember.builder()
									.ensTeamNo(ensTeamNo) //팀번호
									.ensInstCode(mr.getParameter("inst"))
									.ensMemPosition(mr.getParameter("position"))
									.ensMemNo(memNo) //회원번호
									.build());
			
			
			int result = es.insertTeam(ensTeam, musicList, videoList, timeList, ensMemList);
			
			if(result>0) System.out.println("팀 등록 성공");
			
			
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
