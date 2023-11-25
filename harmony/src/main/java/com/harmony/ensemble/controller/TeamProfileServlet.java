package com.harmony.ensemble.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.ensemble.model.dto.EnsembleMember;
import com.harmony.ensemble.model.dto.EnsembleTeam;
import com.harmony.ensemble.model.dto.EnsembleTeamMusic;
import com.harmony.ensemble.model.dto.EnsembleTeamTime;
import com.harmony.ensemble.model.dto.EnsembleTeamVideo;
import com.harmony.ensemble.model.dto.Genre;
import com.harmony.ensemble.model.dto.MemberProfile;
import com.harmony.ensemble.model.service.EnsembleService;
import com.harmony.model.dto.Member;

/**
 * Servlet implementation class TeamProfile
 */
@WebServlet("/ensemble/teamProfile.do")
public class TeamProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeamProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EnsembleService es = new EnsembleService();
		
		//로그인한 멤버의 회원번호와 일치하는 회원의 팀 번호 가져오기.
		Member loginMember = (Member)request.getSession().getAttribute("loginMember");
		String loginMemNo = loginMember.getMemNo();
		
		String teamNo = es.selectTeamNoByMemNo(loginMemNo);
		
		//팀 번호로 팀 정보 가져오기
		EnsembleTeam team= es.selectTeamByNo(teamNo);
//		List<EnsembleTeam> comments=es.selectTeamComment(teamNo);
		
		request.setAttribute("team", team);
//		request.setAttribute("comments", comments);
		
		//장르
		List<Genre> genre = new EnsembleService().searchAllGenre();
		request.setAttribute("genre", genre);

		
		//팀 번호 일치하는 time, music, video 테이블 정보
		List<EnsembleTeamTime> time = es.selectTimes(teamNo);
		request.setAttribute("time", time);
		
		List<EnsembleTeamMusic> music = es.selectMusics(teamNo);
		request.setAttribute("music", music);
		
		List<EnsembleTeamVideo> video = es.selectVideos(teamNo);
		request.setAttribute("video", video);
		
		List<MemberProfile> members = es.selectMemProfile(teamNo);
		request.setAttribute("members", members);
		
		
		
		request.getRequestDispatcher("/views/ensemble/teamProfile.jsp").forward(request, response);
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
