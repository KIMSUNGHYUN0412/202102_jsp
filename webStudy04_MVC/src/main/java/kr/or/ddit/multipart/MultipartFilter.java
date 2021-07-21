package kr.or.ddit.multipart;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultipartFilter implements Filter{
	private static final Logger logger = LoggerFactory.getLogger(MultipartFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("{} 초기화", getClass().getSimpleName());
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//요청이 파일을 포함한 멀티파트 요청인지 판단 - 요청의 헤더 (multipart/form-data)
		HttpServletRequest req = (HttpServletRequest) request;
		String contentType = req.getContentType();
		if(contentType!=null && contentType.startsWith("multipart/form-data")) {
			request = new StandardMultipartHttpServletRequest(req);  
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		logger.info("{} 소멸", getClass().getSimpleName());
	}

}
