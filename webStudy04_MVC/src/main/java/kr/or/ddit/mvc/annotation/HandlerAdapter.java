package kr.or.ddit.mvc.annotation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Front로부터 전달받은 핸들러에 대한 정보를 바탕으로
 * command handler 를 호출하는 역할 수행.
 *
 */
public interface HandlerAdapter {
	
	/**
	 * @param mappingInfo
	 * @param request 
	 * @param response
	 * @return command handler에서 받은 논리적인 viewName
	 * @throws ServletException
	 * @throws IOException
	 */
	public String invokeHandler(RequestMappingInfo mappingInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
