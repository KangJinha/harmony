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

import com.harmony.message.service.MessageService;
import com.harmony.model.dto.Member;

/**
 * Servlet Filter implementation class MessageChecker
 */
@WebFilter(urlPatterns = {"/*"})
public class MessageChecker extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public MessageChecker() {
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
		Member loginMember =(Member)req.getSession().getAttribute("loginMember");
		
		if(loginMember!=null) {
			int unReadMessage = MessageService.getService().getUnreadMessage(loginMember.getMemNo());
			req.setAttribute("unReadMessage", String.valueOf(unReadMessage));
		}
		
		// pass the request along the filter chain
		chain.doFilter(req, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
