package com.harmony.common.fileter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

/**
 * Servlet Filter implementation class MultipartContentFilter
 */
@WebFilter(servletNames = {"ajaxCarouselManage","adminNoticeWrite"})
public class MultipartContentFilter extends HttpFilter implements Filter {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = -6405185061644441860L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public MultipartContentFilter() {
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
		if(req.getMethod().equals("POST")&&!ServletFileUpload.isMultipartContent(req)) {
			HttpServletResponse rsp = (HttpServletResponse)response;
			PrintWriter out = rsp.getWriter();
			out.write("is not Multipart Request");
			out.flush();
			out.close();
			return;
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
