package kr.or.ddit.buyer.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import kr.or.ddit.buyer.service.BuyerService;
import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.utils.ValidatorUtils;
import kr.or.ddit.validate.groups.InsertGroup;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.ProdVO;

@Controller
public class BuyerInsertControllerServlet extends HttpServlet{
	private BuyerService service = BuyerServiceImpl.getInstance();
	private OthersDAO othersDAO = new OthersDAOImpl();
	
	private void addAttribute(HttpServletRequest req) {
		List<Map<String, Object>> lprodList = othersDAO.selectLprodList();
		req.setAttribute("lprodList", lprodList);
		req.setAttribute("req", "INSERT");
	}
	
	
	@RequestMapping("/buyer/buyerInsert.do")
	public String buyerInsertForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		addAttribute(req); 
		return "buyer/buyerForm";

	}
	 
	@RequestMapping(value="/buyer/buyerInsert.do",method=RequestMethod.POST)
	public String buyerInsert(
			@ModelAttribute("buyer") BuyerVO buyer, 
			HttpServletRequest req){
		//PK는 vo에 없음
		//buyerLgu(상품분류코드)는  select로 받기 
		//buyer테이블 제약조건에 따른 검증 (not null)하기
		addAttribute(req);  
		//buyerVO에 담긴 입력 정보 폼으로 돌아갈때 가져가기 
	
		Map<String, List<String>> errors = new HashMap<>(); 
		req.setAttribute("errors", errors);
		ValidatorUtils<BuyerVO> utils = new ValidatorUtils<>();
		boolean valid = utils.validate(buyer, errors, InsertGroup.class);
		
		String viewName =  null;
		String message = null;
		
		if(valid) {
			ServiceResult result = service.createBuyer(buyer);
			
			switch(result) {
			case OK:
//			buyerList이동, redirect
				viewName = "redirect:/buyer/buyerList.do";
				break; 
			default:  
//			buyerForm.jsp이동, forward 
				viewName = "buyer/buyerForm"; 
				message = "서버 오류,ㅈㅅ 다시 해주세염";
				break;
			}  
		}else {
//			buyerForm.jsp이동 , forward
			viewName = "buyer/buyerForm";
		}
		req.setAttribute("message", message);
		
		return viewName;
		
		 
	}
}
