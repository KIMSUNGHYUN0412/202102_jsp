package kr.or.ddit.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.board.service.FreeBoardService;
import kr.or.ddit.board.service.FreeBoardService.CountType;
import kr.or.ddit.board.service.FreeBoardServiceImpl;
import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.vo.ExtendSearchVO;
import kr.or.ddit.vo.FreeBoardVO;
import kr.or.ddit.vo.PagingVO;

@Controller
public class FreeBoardRetrieveController {
	private FreeBoardService service = FreeBoardServiceImpl.getInstance();
	  
	@RequestMapping("/board/boardList.do")
	public String freeBoardList(
			@RequestParam(value="page", required=false, defaultValue="1") int currentPage,
			@ModelAttribute("simpleSearch") ExtendSearchVO simpleSearch,
			HttpServletRequest req) {
		//동기방식 게시글 조회
		//최근 작성글이 먼저 나오게 목록 조회 ,조회수, 추천수 순
		//검색-simpleSearch   
		//검색조건- 제목, 작성자명, 내용, +기간별!
		PagingVO<FreeBoardVO> pagingVO = new PagingVO<>(10, 5);
		pagingVO.setCurrentPage(currentPage);
		pagingVO.setSimpleSearch(simpleSearch); 
		int totalRecord = service.retrieveBoardCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		 
		List<FreeBoardVO> boardList = service.retrieveBoardList(pagingVO);
		pagingVO.setDataList(boardList); 
		req.setAttribute("pagingVO", pagingVO);
		
		return "board/boardList";
	}
	
	@RequestMapping("/board/boardView.do")
	public String boardView(
			@RequestParam("what") int boNo,
			HttpServletRequest req, HttpSession session) {
		  
		FreeBoardVO board = service.retrieveBoard(boNo);
		req.setAttribute("board", board);
		 
		return "board/boardView";
	}
	
	@RequestMapping(value="/board/boardView.do", method=RequestMethod.POST)
	public String incrementCount(
			@RequestParam("what") int boNo,
			@RequestParam("countType") String type,
			HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		ServiceResult result = null;
		if(CountType.RECOMMEND.name().equals(type)) {
			result = service.incrementCount(boNo, CountType.RECOMMEND);	  
		}else {
			result = service.incrementCount(boNo, CountType.REPORT);	  
		} 
		
		String accept = req.getHeader("accept");
		if(StringUtils.containsIgnoreCase(accept, "json")) {
			resp.setContentType("json/application;charset=utf-8");
			ObjectMapper mapper = new ObjectMapper();
			try( 
				PrintWriter out = resp.getWriter();
				){ 
 				mapper.writeValue(out, result);
			}
			return null;
		}else {
			return "board/boardView";
		}
	}
	
}
