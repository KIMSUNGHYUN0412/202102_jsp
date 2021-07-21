package kr.or.ddit.prop.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.prop.service.DataBasePropertyService;
import kr.or.ddit.prop.service.DataBasePropertyServiceImpl;
import kr.or.ddit.vo.DataBasePropertyVO;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;

/**
 * 요청을 받고, 분석하고, 로직을 사용하고, 로직으로부터  MODEL데이터 확보. 
 * VIEW를 선택하고, MODEL을 전달함. -> Controller Layer
 */
 
@WebServlet("/11/jdbcDesc.do")
public class DataBasePropertyController extends HttpServlet{
	
	private DataBasePropertyService service = new DataBasePropertyServiceImpl();
	
	@Override 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8"); 
		
		String accept = req.getHeader("accept");
		String pageParam = req.getParameter("page"); 
 		String search = req.getParameter("search");
// 		String searchType = req.getParameter("searchType");
//		String searchWord = req.getParameter("searchWord");
//		SearchVO simpleSearch = new SearchVO(searchType, searchWord);
		int currentPage = 1;
		if(StringUtils.isNumeric(pageParam)) {
			currentPage = Integer.parseInt(pageParam);
		}
		
		PagingVO<DataBasePropertyVO> pagingVO = new PagingVO<>(5, 3);
		pagingVO.setCurrentPage(currentPage);
		DataBasePropertyVO param = new DataBasePropertyVO();
		param.setPropertyName(search);
		param.setPropertyValue(search);
		param.setDescription(search);
		pagingVO.setDetailSearch(param);
		  
		List<DataBasePropertyVO> propList = service.retrieveDataBaseProperties(pagingVO);
		
		if(accept.contains("json")) {  
			resp.setContentType("application/json;charset=utf-8");
			ObjectMapper mapper = new ObjectMapper();  
			try(
				PrintWriter out = resp.getWriter();
					){  
				out.println(mapper.writeValueAsString(pagingVO));
			}
		}else {
			req.setAttribute("contentsPage", "/WEB-INF/views/11/jdbcDesc.jsp");
			String dest = "/WEB-INF/views/template.jsp";  
			req.getRequestDispatcher(dest).forward(req, resp);
		}
	} 
}
