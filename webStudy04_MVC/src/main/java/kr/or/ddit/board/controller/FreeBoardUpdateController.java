package kr.or.ddit.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.or.ddit.board.service.FreeBoardService;
import kr.or.ddit.board.service.FreeBoardServiceImpl;
import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.multipart.MultipartFile;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.utils.ValidatorUtils;
import kr.or.ddit.validate.groups.UpdateGroup;
import kr.or.ddit.vo.FreeBoardVO;

@Controller
public class FreeBoardUpdateController {
	private FreeBoardService service = FreeBoardServiceImpl.getInstance();
	
	@RequestMapping("/board/boardUpdate.do")
	public String boardUpdateForm(
			@RequestParam("boNo") int boNo,
			HttpServletRequest req) {
		FreeBoardVO freeboard = service.retrieveBoard(boNo);
		req.setAttribute("board", freeboard); 
		req.setAttribute("req", "update"); 
		return "board/boardForm";  
	} 
	
	@RequestMapping(value="/board/boardUpdate.do", method=RequestMethod.POST)
	public String boardUpdate(
			@ModelAttribute("board") FreeBoardVO freeboard,
			@RequestPart(value="boFiles", required=false)  MultipartFile[] boFiles,
			HttpServletRequest req
			) { 
		if(boFiles!=null) freeboard.setBoFiles(boFiles);  
		req.setAttribute("req", "update");  
		
		Map<String, List<String>> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		ValidatorUtils<FreeBoardVO> utils = new ValidatorUtils<>();
		boolean valid = utils.validate(freeboard, errors, UpdateGroup.class);
  
		String viewName = null; 
		String message = null; 

		FreeBoardVO board = service.retrieveBoard(freeboard.getBoNo());
		if(valid) {
			ServiceResult result = service.modifyBoard(freeboard);
			
			switch(result) {
			case OK:
//			boardView로 이동, redirect
				viewName = "redirect:/board/boardView.do?what=" + freeboard.getBoNo();
				break; 
			case INVALIDPASSWORD: 
//			boardForm.jsp로 이동, forward 
				freeboard.setAttatchList(board.getAttatchList());  
				message = "비밀번호 오류";
				viewName = "board/boardForm"; 
				break;  
			default:   
//			boardForm.jsp로 이동, forward 
				viewName = "board/boardForm";
				message = "서버 오류,ㅈㅅ 다시 해주세염";
				break;
			}  
		}else { 
			freeboard.setAttatchList(board.getAttatchList());   
//			boardForm.jsp로 이동, forward
			viewName = "board/boardForm"; 
		}
 
		
		if(message!=null) 
			req.setAttribute("message", message);
		
		return viewName;
		
	}
}
