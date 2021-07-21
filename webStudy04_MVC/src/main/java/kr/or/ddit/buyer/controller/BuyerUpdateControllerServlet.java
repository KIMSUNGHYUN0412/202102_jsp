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
import org.apache.commons.lang3.StringUtils;

import kr.or.ddit.buyer.service.BuyerService;
import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestParam;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.utils.ValidatorUtils;
import kr.or.ddit.validate.groups.InsertGroup;
import kr.or.ddit.validate.groups.UpdateGroup;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.ProdVO;

@Controller
public class BuyerUpdateControllerServlet extends HttpServlet{
	BuyerService service = BuyerServiceImpl.getInstance();
	
	@RequestMapping("/buyer/buyerUpdate.do")
	public String buyerUpdateForm(
			@RequestParam("what") String buyerId, HttpServletRequest req){
		req.setAttribute("req", "UPDATE"); 
	
		BuyerVO buyer = service.retrieveBuyer(buyerId);
		req.setAttribute("buyer", buyer); 
		 
		 return "buyer/buyerForm";
	}
	 
	@RequestMapping(value="/buyer/buyerUpdate.do", method=RequestMethod.POST)
	public String buyerUpdate(
			@ModelAttribute("buyer") BuyerVO buyer, 
			HttpServletRequest req){
		req.setAttribute("req", "UPDATE");
		// buyer에 담긴 입력 정보 폼으로 돌아갈때 가져가기
		
		   
		Map<String, List<String>> errors = new HashMap<>(); 
		req.setAttribute("errors", errors); 
		ValidatorUtils<BuyerVO> utils = new ValidatorUtils<>();
		boolean valid = utils.validate(buyer, errors, UpdateGroup.class);
		
		String viewName =  null;
		String message = null;
		
		if(valid) {
			ServiceResult result = service.modifyBuyer(buyer);
			
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
