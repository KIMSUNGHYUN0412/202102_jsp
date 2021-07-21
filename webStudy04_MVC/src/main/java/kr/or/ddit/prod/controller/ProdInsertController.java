package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.multipart.MultipartFile;
import kr.or.ddit.mvc.annotation.RequestMethod;
import kr.or.ddit.mvc.annotation.resolvers.ModelAttribute;
import kr.or.ddit.mvc.annotation.resolvers.RequestPart;
import kr.or.ddit.mvc.annotation.stereotype.Controller;
import kr.or.ddit.mvc.annotation.stereotype.RequestMapping;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.dao.OthersDAOImpl;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.utils.ValidatorUtils;
import kr.or.ddit.validate.groups.InsertGroup;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.ProdVO;

@Controller
public class ProdInsertController{

	ProdService service = new ProdServiceImpl();
	OthersDAO othersDAO = new OthersDAOImpl();

	private void addAttribute(HttpServletRequest req) {
		List<BuyerVO> buyerList = othersDAO.selectBuyerList();
		req.setAttribute("buyerList", buyerList);

		List<Map<String, Object>> lprodList = othersDAO.selectLprodList();
		req.setAttribute("lprodList", lprodList);
		
		req.setAttribute("req", "INSERT");
	} 

	@RequestMapping("/prod/prodInsert.do")
	public String prodInsertForm(HttpServletRequest req){
		addAttribute(req); 
		return "prod/prodForm"; 
	}
 
	@RequestMapping(value="/prod/prodInsert.do", method=RequestMethod.POST)
	public String prodInsert(@ModelAttribute("prod") ProdVO prod, 
			@RequestPart("prodImage") MultipartFile prodImage,
			HttpServletRequest req){
		addAttribute(req);  
		// prod에 담긴 입력 정보 폼으로 돌아갈때 가져가기
		prod.setProdImage(prodImage); 

		Map<String, List<String>> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		ValidatorUtils<ProdVO> utils = new ValidatorUtils<>();
		boolean valid = utils.validate(prod, errors, InsertGroup.class);

		String viewName = null;
		String message = null;
		
		if(valid) {
			ServiceResult result = service.createProd(prod);
			
			switch(result) {
			case OK:
//			prodList로 이동, redirect
				viewName = "redirect:/prod/prodView.do?what=" + prod.getProdId();
				break; 
			default:  
//			memberForm.jsp로 이동, forward 
				viewName = "prod/prodForm";
				message = "서버 오류,ㅈㅅ 다시 해주세염";
				break;
			}  
		}else { 
//			memberForm.jsp로 이동, forward
			viewName = "prod/prodForm"; 
		}
		req.setAttribute("message", message);
		
		return viewName;
		
	} 


}
