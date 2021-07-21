package kr.or.ddit.buyer.controller;

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

import kr.or.ddit.buyer.service.BuyerService;
import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.commons.exception.DataNotFoundException;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

@Controller 
public class BuyerRetrieveController{
	
	private BuyerService service = BuyerServiceImpl.getInstance();
	private OthersDAO othersDAO = new OthersDAOImpl();
	
	private void addAttribute(HttpServletRequest req) {
		List<BuyerVO> buyerList = othersDAO.selectBuyerList();
		req.setAttribute("buyerList", buyerList); 
		
		List<Map<String, Object>> lprodList = othersDAO.selectLprodList();
		req.setAttribute("lprodList", lprodList);
		  
	}
	
	@RequestMapping("/buyer/buyerList.do")
	public String buyerList(
			@RequestParam(value="page", required=false, defaultValue="1") int currentPage
			,@ModelAttribute("detailSearch") BuyerVO detailSearch
			,HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//거래처명과 상품분류명으로 상세검색 - detailSearch
		addAttribute(req); 
		String accept = req.getHeader("accept");
		
		PagingVO<BuyerVO> pagingVO = new PagingVO<>(5,2);
		pagingVO.setCurrentPage(currentPage);
		
		pagingVO.setDetailSearch(detailSearch);
		//dataList는 service에서 pagingVO에 담아줌(콜바레)
		service.retrieveBuyerList(pagingVO);
		
 		
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
			return "/buyer/buyerList";
			 
		}
		
	}
	
	@RequestMapping("/buyer/buyerView.do")
	public String buyerView(@RequestParam("what") String buyerId,HttpServletRequest req) {
		
		BuyerVO buyer = service.retrieveBuyer(buyerId);
		req.setAttribute("buyer", buyer);   
		    
		return "buyer/buyerView";
		
	}
}
