package kr.or.ddit.commons;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
@WebServlet("/login/logout.do") 
public class LogoutServlet extends HttpServlet{
	@Override 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if(session==null || session.isNew()) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "로그아웃이 최초의 요청???????");
			return;
		}
		session.invalidate(); //세션만료시키기  
		String message = URLEncoder.encode("로그아웃 성공", "UTF-8");
		resp.sendRedirect(req.getContextPath() + "/?message="+message);
		
		
		
	}
}
