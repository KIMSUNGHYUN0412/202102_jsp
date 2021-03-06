package kr.or.ddit.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.or.ddit.board.service.FreeBoardService;
import kr.or.ddit.board.service.FreeBoardServiceImpl;
import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.utils.ValidatorUtils;
import kr.or.ddit.validate.groups.DeleteGroup;
import kr.or.ddit.validate.groups.UpdateGroup;
import kr.or.ddit.vo.FreeBoardVO;

@Controller
public class FreeBoardDeleteController {
	private FreeBoardService service = FreeBoardServiceImpl.getInstance();
	
	@RequestMapping(value="/board/boardDelete.do" , method=RequestMethod.POST)
	public String boardDelete(
			@ModelAttribute("board")FreeBoardVO freeboard,
			HttpSession session) {
	
		String viewName = null; 
		String message = null;  
	
		ServiceResult result = service.removeBoard(freeboard); 
		   
		switch(result) {
		case OK: 
//			boardList로 이동, redirect
			viewName = "redirect:/board/boardList.do";
			break; 
		case INVALIDPASSWORD: 
//			boardView.jsp로 이동, forward 
			message = "비밀번호 오류";
			viewName = "redirect:/board/boardView.do?what=" + freeboard.getBoNo(); 
			break;  
		default:   
//			boardView.jsp로 이동, forward  
			viewName =  "redirect:/board/boardView.do?what=" + freeboard.getBoNo(); 
			message = "서버 오류,ㅈㅅ 다시 해주세염";
			break;
		}   
	
		
		if(message!=null) 
			session.setAttribute("message", message);
		
		return viewName;
		
	}
}
