package kr.or.ddit.auth;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.vo.MemberVO;

public class AuthorizationFilter implements Filter{
	private static final Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("{} 초기화", getClass().getSimpleName()); 
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		uri = uri.substring(req.getContextPath().length());
		Map<String, String[]> securedMap = (Map) req.getServletContext().getAttribute("securedMap");
		boolean pass = true;
		//보호자원 여부 판단
		if(securedMap.containsKey(uri)){ 
			//보호자원이면 권한,ROLE 비교 
			MemberVO authMember = (MemberVO) req.getSession().getAttribute("authMember");
			String userRole = authMember.getMemRole();
			String[] resRoles = securedMap.get(uri);
			pass = Arrays.binarySearch(resRoles, userRole) >= 0; 
		} 
		if(pass) {
			chain.doFilter(request, response);
		}else {
			//권한이 없으면 400에러 
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED); 
		}
		
	}

	@Override
	public void destroy() {
		logger.info("{} 소멸", getClass().getSimpleName()); 
		
	}
	
}
