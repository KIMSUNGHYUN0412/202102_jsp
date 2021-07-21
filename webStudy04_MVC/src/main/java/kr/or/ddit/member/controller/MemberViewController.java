package kr.or.ddit.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.commons.UserNotFoundException;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.vo.MemberVO;

/**
 * POJO(Plain Old Java Object) 
 *
 */
public class MemberViewController{
	// member service와의 의존관계
	private MemberService service = MemberServiceImpl.getInstance();

	public String view(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 파라미터 받아오기
		String mem_id = req.getParameter("who");
		MemberVO member = null;
		// 검증 : 없으면 400에러
		if (StringUtils.isBlank(mem_id)) {
			resp.sendError(400, "필수파라미터 누락");
			return null;
		} 

		// service메소드
		try{
			member = service.retrieveMember(mem_id);
			// usernotfoundexception 처리 404에러
			req.setAttribute("member", member);
			 
			return "member/memberView";
		}catch (UserNotFoundException e) {
			resp.sendError(404, e.getMessage());
			return null;
		}

	}
	 
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		//인코딩 
//		resp.setCharacterEncoding("utf-8");
//		
//		// 파라미터 받아오기
//		String mem_id = req.getParameter("who");
//		MemberVO member = null;
//		// 검증 : 없으면 400에러
//		if (StringUtils.isBlank(mem_id)) {
//			resp.sendError(400, "필수파라미터 누락");
//			return;
//		}
//
//		// service메소드
//		try {
//			member = service.retrieveMember(mem_id);
//			// usernotfoundexception 처리 404에러
//			req.setAttribute("member", member);
//  
//			String dest = "/WEB-INF/views/member/memberView.jsp";
//			// jsp로 보내기
//			req.getRequestDispatcher(dest).forward(req, resp);
//		} catch (UserNotFoundException e) {
//
//		}
//
//	}
}
