package com.biginformatique.helpdesk.util;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class CharacterEncodingFilter implements Filter {

	 private FilterConfig fc; 

	    @Override
	    public void destroy() {
	        // TODO Auto-generated method
	    }

	    @Override
	    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
	            throws IOException, ServletException {
	        HttpServletRequest request = (HttpServletRequest) req; 
	        HttpServletResponse response = (HttpServletResponse) resp; 
	        

	        request.setCharacterEncoding("UTF8");
	        //response.setContentType("text/html; charset=UTF-8");
	       // response.setContentType("application/json; charset=UTF-8");
	        response.setCharacterEncoding("UTF8"); 

	        chain.doFilter(request, response); 

//	        request.setCharacterEncoding("UTF8"); 
//	        response.setCharacterEncoding("UTF8"); 
	    }

	    @Override
	    public void init(FilterConfig arg0) throws ServletException {
	        // TODO Auto-generated method stub
	    }
}
