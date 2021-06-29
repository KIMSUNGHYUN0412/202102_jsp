package kr.or.ddit.prop.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.prop.service.DataBasePropertyService;
import kr.or.ddit.prop.service.DataBasePropertyServiceImpl;
import kr.or.ddit.vo.DataBasePropertyVO;

/**
 * 요청을 받고, 분석하고, 로직을 사용하고, 로직으로부터  MODEL데이터 확보. 
 * VIEW를 선택하고, MODEL을 전달함. -> Controller Layer
 */
 
@WebServlet("/11/jdbcDesc.do")
public class DataBasePropertyController extends HttpServlet{
	
	private DataBasePropertyService service = new DataBasePropertyServiceImpl();
	
	@Override 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String accept = req.getHeader("accept");
		List<DataBasePropertyVO> propList = service.retrieveDataBaseProperties();
		
		if(accept.contains("json")) {
			resp.setContentType("application/json;charset=utf-8");
			ObjectMapper mapper = new ObjectMapper();  
			try(
				PrintWriter out = resp.getWriter();
					){
				out.println(mapper.writeValueAsString(propList));
			}
		}else {
			req.setAttribute("contentsPage", "/WEB-INF/views/11/jdbcDesc.jsp");
			String dest = "/WEB-INF/views/template.jsp";  
			req.getRequestDispatcher(dest).forward(req, resp);
		}
	} 
}
