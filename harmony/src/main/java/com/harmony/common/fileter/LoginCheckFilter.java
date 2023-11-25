package com.harmony.common.fileter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

import com.harmony.model.dto.Member;

/**
 * Servlet Filter implementation class LoginCheckFilter
 */
@WebFilter(urlPatterns = {"/member/myPageServlet.do"})
public class LoginCheckFilter extends HttpFilter implements Filter {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = -3963319287910930360L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public LoginCheckFilter() {
        super();
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// place your code here
		HttpServletRequest req = (HttpServletRequest)request;
		Member loginMember = (Member)req.getSession().getAttribute("loginMember");
		if(loginMember == null) {
			req.setAttribute("msg", "로그인이 필요한 서비스입니다.");
			req.setAttribute("loc", "/main.do");
			req.getRequestDispatcher("/views/lesson/common/msg.jsp").forward(req, response);
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
