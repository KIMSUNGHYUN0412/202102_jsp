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
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.utils.ValidatorUtils;
import kr.or.ddit.validate.groups.InsertGroup;
import kr.or.ddit.vo.FreeBoardVO;

@Controller
public class FreeBoardInsertController {
	
	private FreeBoardService service = FreeBoardServiceImpl.getInstance();
	
	@RequestMapping("/board/boardInsert.do") 
	public String boardInsertForm(
			@ModelAttribute("board") FreeBoardVO freeboard,
			HttpServletRequest req) {
		req.setAttribute("req", "insert");  
		return "board/boardForm"; 
 	} 
	
	@RequestMapping(value="/board/boardInsert.do", method=RequestMethod.POST)
	public String boardInsert( 
			@ModelAttribute("board") FreeBoardVO freeboard,
			@RequestPart(value="boFiles", required=false)  MultipartFile[] boFiles,
			HttpServletRequest req) {
		req.setAttribute("req", "insert");
		
		if(boFiles!=null)freeboard.setBoFiles(boFiles);  
		
		Map<String, List<String>> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		ValidatorUtils<FreeBoardVO> utils = new ValidatorUtils<>();
		boolean valid = utils.validate(freeboard, errors, InsertGroup.class);

		String viewName = null; 
		String message = null; 
		 
		if(valid) {
			ServiceResult result = service.createBoard(freeboard);
			
			switch(result) {
			case OK:
//			boardView로 이동, redirect
				viewName = "redirect:/board/boardView.do?what=" + freeboard.getBoNo();
				break; 
			default:  
//			boardForm.jsp로 이동, forward 
				viewName = "board/boardForm";
				message = "서버 오류,ㅈㅅ 다시 해주세염";
				break;
			}  
		}else { 
//			boardForm.jsp로 이동, forward
			viewName = "board/boardForm"; 
		}
		req.setAttribute("message", message);
		 
		return viewName;
	}
}
