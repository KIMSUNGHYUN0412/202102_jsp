package kr.or.ddit.servlet07;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calculate.do")
public class CalculateServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mime = req.getParameter("mime");
		String left = req.getParameter("left"); 
		String operator = req.getParameter("operator");
		String right = req.getParameter("right"); 
		int status = 200;
		String errorMessage = null;
		
		if(mime==null || mime.isEmpty()) {
			mime = req.getHeader("accept");
		}
 
		if(left!=null && operator!=null && right!=null) {
			if(left.matches("[0-9]{1,2}") && right.matches("[0-9]{1,2}")) {
				
			}else { 
				status = 400;
				errorMessage = "숫자 입력하시라구요";
			} 
		}else {
			status = 400;
			errorMessage = "필수파라미터 누락";
		}
		
		if(status!=200) {
			resp.sendError(status, errorMessage);
		}
		 
		int leftnum = Integer.parseInt(left);
		int rightnum = Integer.parseInt(right);
		
		
		
	}
}
