package kr.or.ddit.servlet07;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/bts")
public class BTSServlet extends HttpServlet{
	
	private ServletContext application;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);  
		application = getServletContext();
		Map<String, String[]> btsMap = new HashMap<>();
		btsMap.put("B001", new String[] {"rm", "rm.jsp"});
		btsMap.put("B002", new String[] {"jin", "jin.jsp"});
		btsMap.put("B003", new String[] {"suga", "suga.jsp"});
		btsMap.put("B004", new String[] {"jhope", "jhope.jsp"});
		btsMap.put("B005", new String[] {"jimin", "jimin.jsp"});
		btsMap.put("B006", new String[] {"v", "v.jsp"});
		btsMap.put("B007", new String[] {"jungkook", "jungkook.jsp"});
		application.setAttribute("btsMap", btsMap); 
	}
	 
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		String goPage = "/WEB-INF/views/08/btsForm.jsp";
		req.getRequestDispatcher(goPage).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String member = req.getParameter("btsMember");
		int status = HttpServletResponse.SC_OK;
		String errorMsg = null;
		String goPage = null;
		if(member==null || member.isEmpty()) {
			status = 400;
			errorMsg = "필수 파라미터 누락";

		} 
		
		Map<String, String[]> btsMap = (Map) application.getAttribute("btsMap");
		if(!btsMap.containsKey(member)) {
			status = 404; 
			errorMsg = String.format("방탄에는 %s 멤버가 없음.", member);
		}else {
			Date searchTime = new Date();
			req.setAttribute("searchTime", searchTime); 
			String[] info = btsMap.get(member);
			goPage = info[1];
		} 
		
		if(status==HttpServletResponse.SC_OK) {
			goPage = "/WEB-INF/views/bts/" + goPage;
			req.getRequestDispatcher(goPage).forward(req, resp);
		}else {
			resp.sendError(status, errorMsg);
			return;
		}
	}
}
