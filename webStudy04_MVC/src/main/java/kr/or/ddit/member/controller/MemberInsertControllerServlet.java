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
import kr.or.ddit.validate.groups.InsertGroup;
import kr.or.ddit.vo.MemberVO;

@Controller
public class MemberInsertControllerServlet extends HttpServlet{
	
	private MemberService service = MemberServiceImpl.getInstance();
	
	
	@RequestMapping("/member/memberInsert.do")
	public String memberInsertForm(HttpServletRequest req) {
		req.setAttribute("req", "INSERT");  
		return "member/memberForm";  
	} 
	 
	@RequestMapping(value="/member/memberInsert.do", method=RequestMethod.POST)
	public String memberInsert(
			@ModelAttribute("member") MemberVO member
			,@RequestPart(value="memImage", required=false) MultipartFile memImage
			,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("req", "INSERT");  
		//req변경 확인
		member.setMemImage(memImage);  
		
		Map<String, List<String>> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		ValidatorUtils<MemberVO> utils = new ValidatorUtils<>(); 
		boolean valid = utils.validate(member, errors, InsertGroup.class);
		
		String viewName = null;
		String message = null;
		
		if(valid) {
			ServiceResult result = service.createMember(member); 
			
			switch(result) {
			case OK:
//			index로 이동, redirect 
				viewName = "redirect:/";
				break;  
			case PKDUPLICATED: 
//			memberForm.jsp로 이동, forward
				viewName = "member/memberForm";
				message = "아이디 중복"; 
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
