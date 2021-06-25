package kr.or.ddit.commons;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import kr.or.ddit.vo.MenuVO;
import kr.or.ddit.vo.ServiceInfoVO;

@WebServlet(value="/index.do", loadOnStartup=1)
public class IndexServlet extends HttpServlet{
	private ServletContext application;
	 
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try(
			InputStream is = getClass().getResourceAsStream("../serviceinfo.xml");
		){
			application = getServletContext();
			JAXBContext jaxbContext = JAXBContext.newInstance(ServiceInfoVO.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			ServiceInfoVO infoVO = (ServiceInfoVO) unmarshaller.unmarshal(is);
			application.setAttribute("serviceInfo", infoVO); 
		}catch(Exception e){
			throw new ServletException(e);
		}
		
	}
	
	@Override 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String service = req.getParameter("service");
		ServiceInfoVO infoVO = (ServiceInfoVO) application.getAttribute("serviceInfo");
		List<MenuVO> menuList = infoVO.getMenuList();
		String contentsPage = null;
		int status = 200;
		String errorMsg = null;
		if(service==null || service.isEmpty()) {
			contentsPage = "/WEB-INF/views/index.jsp";
		}else {
			MenuVO searchCondition = new MenuVO();
			searchCondition.setCode(service);  
			int index = menuList.indexOf(searchCondition);
			if(index!=-1) {
				MenuVO searched = menuList.get(index);
				contentsPage = searched.getLink();
			}else {
				status =404;
				errorMsg = String.format("%s 서비스는 제공 불가", service);
			}
		} 
		
		if(status==200) {
			String dest = "/WEB-INF/views/template.jsp";  
			req.setAttribute("contentsPage", contentsPage);
			req.getRequestDispatcher(dest).forward(req, resp); 
		}else {
			resp.sendError(status, errorMsg); 
		}
		
 	} 
}
