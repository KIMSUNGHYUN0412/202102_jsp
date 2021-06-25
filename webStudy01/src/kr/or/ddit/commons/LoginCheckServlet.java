package kr.or.ddit.commons;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
@SuppressWarnings("serial")
@WebServlet("/login/loginCheck.do")
public class LoginCheckServlet extends HttpServlet{
	private boolean authenticated(String id, String password) {
		return id.equals(password);
	} 
	 
	private boolean validate(String id, String password, Map<String, String> errors) {
		boolean valid = true;
		if(id==null || id.isEmpty()) {
			valid = false;
			if(1==1)
				throw new UserNotFoundException("사용자가 존재하지 않음");
			errors.put("mem_id", "아이디는 필수 입력");
		}
		if(password==null || password.isEmpty()) {
			valid = false;
			errors.put("mem_pass", "비밀번호는 필수 입력");
		}else {
			Pattern regexPtrn = Pattern.compile("^((?=.*[a-z]+)(?=.*[A-Z]+)(?=.*[0-9]+)(?=.*[!@#\\$%\\^\\&\\*]+).{4,8})$");
			Matcher matcher = regexPtrn.matcher(password);
			if(!matcher.find()) {
				valid = false; 
				errors.put("mem_pass", "비밀번호는 영대소문자 숫자 특수문자를 포함한 4~8글자 이내");
			}else {
//				System.out.println(matcher.group(1));
			}
		}
		return valid;
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1. 파라미터 확보
		String id = req.getParameter("mem_id");
		String password = req.getParameter("mem_pass");
		//2. 검증
		HttpSession session = req.getSession();
		Map<String, String> errors = new HashMap<>();
		boolean valid = validate(id, password, errors);
		String goPage = null;
		boolean redirect = false;
		if(!valid) {
			//필수 파라미터 누락 여부 확인(400)
//			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "아이디 비밀번호 다 입력하시라구여 ");
//			return;
			goPage = "/login/loginForm.jsp";
			redirect = true; 
			session.setAttribute("errors", errors);
 		}

		//3. 인증 
		boolean equal = authenticated(id, password);
		if(equal) {
			//	1) 성공 : welcome page로 이동 (redirect)
			goPage = "/";
			redirect = true;
			session.setAttribute("authId", id); 
		}else {
			//	2) 실패 : login form page로 이동 (forward) 
			goPage = "/login/loginForm.jsp";
			redirect = true;
			session.setAttribute("failId", id);
		} 
		
		if(redirect) {
			resp.sendRedirect(req.getContextPath() + goPage);
		}else {
			RequestDispatcher rd = req.getRequestDispatcher(goPage);
			rd.forward(req, resp); 
		}
	}
}
