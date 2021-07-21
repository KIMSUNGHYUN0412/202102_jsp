package kr.or.ddit.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlindFilter implements Filter{
	private static final Logger logger = LoggerFactory.getLogger(BlindFilter.class);
	private Map<String, String> blindMap;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("{} 초기화", getClass().getSimpleName());
		blindMap = new HashedMap();
		blindMap.put("127.0.0.1", "날 필터링 해라 음하하");
		blindMap.put("0:0:0:0:0:0:0:1", "날 필터링 해라 음하핫");
		blindMap.put("192.168.44.19", "세희언니 필터링 해라 음하하"); 
		blindMap.put("192.168.44.84", "진우님 필터링 해라 음하하");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String messageView = "/13/blindMessage.jsp";
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		uri = uri.substring(req.getContextPath().length());
		String clientIp = req.getRemoteAddr();
		boolean pass = uri.equals(messageView) || !blindMap.containsKey(clientIp);
		if(!pass) { 
			String reason = blindMap.get(clientIp);
			req.getSession().setAttribute("reason", reason); 
			resp.sendRedirect(req.getContextPath() + messageView);
		}else {
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void destroy() {
		logger.info("{} 소멸", getClass().getSimpleName());
		
	}

}
