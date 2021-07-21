package kr.or.ddit.member.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.multipart.MultipartFile;
import kr.or.ddit.multipart.StandardMultipartHttpServletRequest;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.utils.ValidatorUtils;
import kr.or.ddit.validate.groups.UpdateGroup;
import kr.or.ddit.vo.MemberVO;

/**
 * 로그인된 사용자가 자기 정보를 수정함
 *
 */
@Controller
public class MemberUpdateControllerServlet{
	private MemberService service = MemberServiceImpl.getInstance();

	@RequestMapping("/member/memberUpdate.do") 
	public String memberUpdateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
			viewName = "member/memberForm"; 
		}
		return viewName;
	}

	@RequestMapping(value="/member/memberUpdate.do", method=RequestMethod.POST)
	public String memberUpdate(
			@ModelAttribute("member") MemberVO member
			,@RequestPart(value="memImage", required=false) MultipartFile memImage
			,HttpServletRequest req) throws IOException {
		  
		member.setMemImage(memImage);  
		
		Map<String, List<String>> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		ValidatorUtils<MemberVO> utils = new ValidatorUtils<>();
		boolean valid = utils.validate(member, errors, UpdateGroup.class);
		 
		String viewName = null;
		String message = null;
		
		if(valid) {
			ServiceResult result = service.modifyMember(member);
			
			switch(result) {
			case OK:
//			mypage로 이동, redirect
				viewName = "redirect:/mypage.do";
				break;
			case INVALIDPASSWORD:
//			memberForm.jsp로 이동, forward
				viewName = "member/memberForm";
				message = "비밀번호 오류";
				break;
			default: 
//			memberForm.jsp로 이동, forward
				viewName = "member/memberForm"; 
				message = "서버 오류,ㅈㅅ 다시 해주세염";
				break;
			}
		}else {
//			memberForm.jsp로 이동, forward
			viewName = "member/memberForm";
		} 
		req.setAttribute("message", message);
		
		
		return viewName;
	}

}
