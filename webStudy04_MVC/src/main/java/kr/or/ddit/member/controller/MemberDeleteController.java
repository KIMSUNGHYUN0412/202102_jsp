package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.vo.MemberVO;

/**
 * 로그인 되어있는 사용자에 대한 탈퇴 처리
 *
 */
@Controller
public class MemberDeleteController{
	private MemberService service = MemberServiceImpl.getInstance();

	@RequestMapping(value="/member/memberDelete.do", method=RequestMethod.POST)
	public String memberDelete( 
			@ModelAttribute("member") MemberVO member
			,HttpServletRequest req){  
		
		HttpSession session = req.getSession();
		MemberVO authMember = (MemberVO)session.getAttribute("authMember");
		member.setMemId(authMember.getMemId());  
		String message = null;
		String viewName = null;
		ServiceResult result = service.removeMember(member); 
		switch (result) {
		case OK:
//			welcome page : redirect 
			session.invalidate(); 
			viewName = "redirect:/";
			break; 
		case INVALIDPASSWORD:
//			mypage : redirect
			message = "비밀번호 오류";
			viewName = "redirect:/mypage.do";
			break;
		default: 
//			mypage : redirect 
			message = "서버 오류, 이따 다시 하셈";
			viewName = "redirect:/mypage.do";
			break; 
		}     
		
		if(message!=null)
			session.setAttribute("message", message);
		
		return viewName;
	}
}
