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

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.member.service.AuthenticateService;
import kr.or.ddit.member.service.AuthenticateServiceImpl;
import kr.or.ddit.vo.MemberVO;
 
@SuppressWarnings("serial")
@WebServlet("/login/loginCheck.do")
public class LoginCheckServlet extends HttpServlet{
	
	private AuthenticateService service = new AuthenticateServiceImpl();
	
	
	 
	private boolean validate(MemberVO param, Map<String, String> errors) {
		boolean valid = true;
		if(StringUtils.isBlank(param.getMemId())) {
			valid = false;
			errors.put("mem_id", "아이디는 필수 입력");
		}
		if(StringUtils.isBlank(param.getMemPass())) { 
			valid = false;
			errors.put("mem_pass", "비밀번호는 필수 입력");
		}
/*		else {
			Pattern regexPtrn = Pattern.compile("^((?=.*[a-z]+)(?=.*[A-Z]+)(?=.*[0-9]+)(?=.*[!@#\\$%\\^\\&\\*]+).{4,8})$");
			Matcher matcher = regexPtrn.matcher(param.getMem_pass());
			if(!matcher.find()) {
				valid = false; 
				errors.put("mem_pass", "비밀번호는 영대소문자 숫자 특수문자를 포함한 4~8글자 이내");
			}else {
//				System.out.println(matcher.group(1));
			}
		}*/
		return valid;
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		//1. 파라미터 확보
		String mem_id = req.getParameter("mem_id");
		String mem_pass = req.getParameter("mem_pass");
		//2. 검증
		HttpSession session = req.getSession();
		Map<String, String> errors = new HashMap<>();
		MemberVO param = MemberVO.builder().memId(mem_id).memPass(mem_pass).build();
		
		boolean valid = validate(param, errors);
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
		try{
			Object resultValue = service.authenticate(param);
			 
			if(resultValue instanceof MemberVO) {
				//	1) 성공 : 가려고했던 page로 이동 (redirect) 
				goPage = (String) session.getAttribute("savedRequestURL");
				session.removeAttribute("savedRequestURL"); 
				if(goPage==null || goPage.isEmpty()) {
					goPage = "/";  
				} 
				redirect = true; 
				// 로그인에 성공하면, 가려고했던 페이지로 이동해서 성공한 회원의 이름을 보여줄것   
				session.setAttribute("authMember", resultValue);  
			}else {
				//	2) 실패 : login form page로 이동 (forward) 
				goPage = "/login/loginForm.jsp";
				redirect = true;
				session.setAttribute("failId", mem_id); 
				session.setAttribute("message", "비밀번호 오류");
			} 
		}catch(UserNotFoundException e){
			goPage = "/login/loginForm.jsp";
			redirect = true; 
			session.setAttribute("message", e.getMessage());
		}
		
		if(redirect) {
			resp.sendRedirect(req.getContextPath() + goPage);
		}else {
			RequestDispatcher rd = req.getRequestDispatcher(goPage);
			rd.forward(req, resp); 
		}
	}
}
