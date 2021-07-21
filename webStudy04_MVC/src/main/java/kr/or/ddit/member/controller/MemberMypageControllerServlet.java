package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.commons.UserNotFoundException;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.vo.MemberVO;

@Controller
public class MemberMypageControllerServlet extends HttpServlet{
	//service 의존관계 
	private MemberService service = MemberServiceImpl.getInstance();
	
	@RequestMapping("/mypage.do")
	public String mypage(HttpServletRequest req){
		// session에서 mem_id 꺼내기 
		HttpSession session = req.getSession();
		MemberVO authMember = (MemberVO)session.getAttribute("authMember");
		String viewName = null;
		if(authMember==null) {  
			req.setAttribute("contentsPage", "/WEB-INF/views/index.jsp");
			viewName = "template"; 
		}else { 
			MemberVO member = service.retrieveMember(authMember.getMemId());
			req.setAttribute("member", member); 
			viewName = "member/memberView"; 
		} 
		return viewName;

	}
}
