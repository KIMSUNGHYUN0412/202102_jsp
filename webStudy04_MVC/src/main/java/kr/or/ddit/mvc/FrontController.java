package kr.or.ddit.mvc;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.mvc.annotation.HandlerAdapter;
import kr.or.ddit.mvc.annotation.HandlerMapping;
import kr.or.ddit.mvc.annotation.RequestMappingHandlerAdapter;
import kr.or.ddit.mvc.annotation.RequestMappingHandlerMapping;
import kr.or.ddit.mvc.annotation.RequestMappingInfo;
import kr.or.ddit.mvc.annotation.resolvers.RequestPartArgumentResolver;

public class FrontController extends HttpServlet{
	
	private HandlerMapping handlerMapping;
	private HandlerAdapter handlerAdapter;
	private ViewResolver viewResolver;
	 
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String basePackage = config.getInitParameter("basePackage");
		String prefix = config.getInitParameter("prefix");
		String suffix = config.getInitParameter("suffix");
		handlerMapping = new RequestMappingHandlerMapping(basePackage);
		handlerAdapter = new RequestMappingHandlerAdapter();
		((RequestMappingHandlerAdapter)handlerAdapter).addArgumentResolvers(new RequestPartArgumentResolver());
		viewResolver = new InternalResourceViewResolver(); 
		((InternalResourceViewResolver)viewResolver).setPrefix(prefix);
		((InternalResourceViewResolver)viewResolver).setSuffix(suffix);
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestMappingInfo mappingInfo = handlerMapping.findCommandHandler(req);
		if(mappingInfo==null) {
			resp.sendError(404, req.getRequestURI()+ "에 대한 핸들러가 없음, 제공하지 않는 서비스");
			return;
		}
		String viewName = handlerAdapter.invokeHandler(mappingInfo, req, resp);
		if(viewName==null) {
			if(!resp.isCommitted()) {
				resp.sendError(500, "논리적인 뷰네임이 결정되지 않음, ㅈㅅ");
			} 
		}else {
			viewResolver.viewResolve(viewName, req, resp); 
		}
	}
	
}
