package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.commons.UserNotFoundException;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;

@Controller
public class MemberRetrieveController{
	//member service와의 의존관계
	private MemberService service = MemberServiceImpl.getInstance();
	
	@RequestMapping("/member/memberList.do") 
	public String list(
			@RequestParam(value="page", required=false, defaultValue="1") int currentPage
			,@ModelAttribute("simpleSearch") SearchVO simpleSearch
			,HttpServletRequest req) {
		 
 		PagingVO<MemberVO> pagingVO = new PagingVO<>(5, 2);
 		pagingVO.setSimpleSearch(simpleSearch);
		pagingVO.setCurrentPage(currentPage); 
		int totalRecord = service.retrieveMemberCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		 
//		memberList 라는 이름의 속성으로 회원 목록 공유
		List<MemberVO> memberList = service.retrieveMemberList(pagingVO);
		pagingVO.setDataList(memberList); 
		req.setAttribute("pagingVO", pagingVO);  
		
		return "member/memberList"; 
		
	}
	
	@RequestMapping("/member/memberView.do")
	public String memberView(@RequestParam("who") String mem_id, HttpServletRequest req) {
		// 파라미터 받아오기
		
		// service메소드  
		MemberVO member = service.retrieveMember(mem_id); 
		req.setAttribute("member", member);
		
		return "member/memberView";
	

	}
}
