package com.harmony.member.ljykakao;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.harmony.member.service.MemberService;
import com.harmony.model.dto.Member;
import com.harmony.model.dto.MemberInfo;

/**
 * Servlet implementation class KakaoLoginServlet
 */
@WebServlet("/member/kakaoLogin.do")
public class KakaoLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KakaoLoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		String kakaoNum ="";
		Member member =null;
		// 로그인 후 리다이렉트 될 경로 지정
         String path = request.getContextPath()+"/main.do";
        String serverInfo = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
        System.out.println(serverInfo);
		try {
	        // URL에 포함된 code를 이용하여 액세스 토큰 발급
			HashMap<String,Object> tokens =KakaoService.getKakaoService().getKakaoAccessToken(code,serverInfo); 
	        String accessToken = String.valueOf(tokens.get("accessToken"));
	        
	        // 액세스 토큰을 이용하여 카카오 서버에서 유저 정보(카카오ID, 닉네임, 이메일) 받아오기
	        HashMap<String, Object> userInfo = KakaoService.getKakaoService().getUserInfo(accessToken);
	        
	        // 카카오 아이디 정보
	         kakaoNum = String.valueOf(userInfo.get("id"));
	        
	        // 같은 카카오ID를 가진 회원이 있는지 확인
	        member = new MemberService().selectMemberByKakaoId(kakaoNum);
	        
	        // 만약 카카오 아이디 정보와 일치하는 회원이 없으면
	        if(member == null){
//	         MemberDTO에 권한(학생), 카카오ID를 넣어 세팅하고
	         member = Member.builder().memAuthority("STUDENT").memKakaoNum(kakaoNum).build();
//	         MemberInfoDTO에 email을 넣어서 세팅하고
	         MemberInfo memInfo = MemberInfo.builder().email(String.valueOf(userInfo.get("email"))).build();
//	         member 객체와 memberInfo객체를 전달해서 DB에 입력후, 저장된 member 정보를 가져옴
	         member = new MemberService().insertMember(member, memInfo);
//	         가입축하메세지 + 다른 정보 추가 창으로 이동하는 경로 지정
	        
	         request.getSession().setAttribute("MemberInfo", memInfo);
	         path = request.getContextPath()+"/member/addIntroduce.do";
	        }
	        
	        
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		// member객체를 loginMember라는 이름으로 세션에 저장
	         request.getSession().setAttribute("loginMember", member);
	         
	        // 지정된 경로로 리다이렉트 하기
	         response.sendRedirect(path);
	}
		
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
