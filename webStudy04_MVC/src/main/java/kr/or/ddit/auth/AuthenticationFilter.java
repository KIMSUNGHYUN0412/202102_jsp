package kr.or.ddit.auth;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.vo.MemberVO;

/**
 * 접근 제어를 통한 서버 보안
 * 인증(Authentication) + 인가(Authorization)
 * 인증 ? 보호자원에 대한 요청을 발생시킨 사용자의 신원을 확인하는 과정.
 * 인가 ? 보호자원에 대한 요청을 발생시킨 사용자가 해당 자원에 대해 접근이 허가되어있는지 확인하는 과정. 
 *
 */
public class AuthenticationFilter implements Filter{
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	private Map<String, String[]> securedMap;
	ServletContext application;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("{} 초기화", getClass().getSimpleName());
		securedMap = new HashMap<>();
		Properties props = new Properties();
		String path = filterConfig.getInitParameter("secured_path");
		try(
			InputStream is = getClass().getResourceAsStream(path);
		) {
			props.loadFromXML(is);
			for(Object key : props.keySet()) {
				Object value = props.get(key);
				String resourceUrl = key.toString().trim();
				String[] roles = value.toString().trim().split(",");
				Arrays.sort(roles);
				securedMap.put(resourceUrl, roles);
				logger.info("{} : {}", resourceUrl, Arrays.toString(roles));
			}
		} catch (IOException e) {
			throw new ServletException(e);
		}
		application = filterConfig.getServletContext(); 
		application.setAttribute("securedMap", securedMap);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//현재 요청이 어떤 자원을 요구하는지 찾기 - requestURI 
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String requestUri = req.getRequestURI();
		requestUri = requestUri.substring(req.getContextPath().length());
		String loginView = "/login/loginForm.jsp"; 
		boolean pass = true;
		//보호자원 여부 판단 - 아니면 통과, 
		if(securedMap.containsKey(requestUri)) {
			//보호자원이면 로그인 여부 판단 - 로그인상태면 통과
			Object authMember = req.getSession().getAttribute("authMember");
			pass = authMember != null;
		}
		
		if(pass) {  
			chain.doFilter(request, response);
		}else {
			//로그인안되어있으면 loginForm으로 보내기  
			req.getSession().setAttribute("savedRequestURL", requestUri); 
			resp.sendRedirect(req.getContextPath() + loginView);
		}
		
	}

	@Override
	public void destroy() {
		logger.info("{} 소멸", getClass().getSimpleName());
		 
	}

}
