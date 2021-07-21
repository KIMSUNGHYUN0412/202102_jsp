package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.commons.exception.DataNotFoundException;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

@Controller
public class ProdRetrieveController{
	
	private ProdService serivce = new ProdServiceImpl();
	private OthersDAO othersDAO = new OthersDAOImpl();
	
	private void addAttribute(HttpServletRequest req) {
		List<BuyerVO> buyerList = othersDAO.selectBuyerList();
		req.setAttribute("buyerList", buyerList); 
		
		List<Map<String, Object>> lprodList = othersDAO.selectLprodList();
		req.setAttribute("lprodList", lprodList);
		  
	}
	
	@RequestMapping("/prod/prodList.do")
	public String prodList(
			@RequestParam(value="page", required=false, defaultValue="1") int currentPage,
			@ModelAttribute("detailSearch") ProdVO detailSearch,
			HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		addAttribute(req);    
		String accept = req.getHeader("accept");

//		검색 조건 : 상품명, 상품분류코드, 거래처코드 (상세 검색)
//		동일 페이지 내에서 정렬 조건 : 상품 분류별 정렬 및 최근 등록된상품부터 조회됨.
		PagingVO<ProdVO> pagingVO = new PagingVO<>(5, 5);

		pagingVO.setCurrentPage(currentPage);
		
		pagingVO.setDetailSearch(detailSearch);
		 
		serivce.retrieveProdList(pagingVO); 

		if(StringUtils.containsIgnoreCase(accept, "json")) {
			resp.setContentType("json/application;charset=utf-8");
			ObjectMapper mapper = new ObjectMapper();
			try( 
				PrintWriter out = resp.getWriter();
				){
 				mapper.writeValue(out, pagingVO);
			}
			return null;
		}else {
			return "prod/prodList";
		}
		
	}
	
	@RequestMapping("/prod/prodView.do")
	public String prodView(@RequestParam("what") String prodId, HttpServletRequest req) {

		ProdVO prod = serivce.retrieveProd(prodId);  
		
		req.setAttribute("prod", prod);
		 
		return "prod/prodView";
		
	}
	
	
}
