package kr.or.ddit.emp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.commons.exception.DataNotFoundException;
import kr.or.ddit.emp.service.EmployeeService;
import kr.or.ddit.emp.service.EmployeeServiceImpl;
import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.vo.EmployeeVO;

@WebServlet("/employee/deleteEmp.do")
public class EmployeeDeleteControllerServlet extends HttpServlet{
	private EmployeeService service = EmployeeServiceImpl.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String empnoStr = req.getParameter("empno");
		
		Map<String, Object> resultMap = new HashMap<>();
		int status = 200;
		ServiceResult result = null;
		if(StringUtils.isNotBlank(empnoStr) && StringUtils.isNumeric(empnoStr)) {
			try {
				int empno = Integer.parseInt(empnoStr);
				result = service.removeEmployee(empno);
				if(ServiceResult.FAIL.equals(result)) {
					resultMap.put("message", "서버 오류 쫌따 다시 실행");
				}else {
					resultMap.put("success", true);
				}
			}catch (DataNotFoundException e) {
				status = 404;
			}
		}else {
			status = 400;
		}
		if(status==200){
			resp.setContentType("application/json;charset=UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			try(
				PrintWriter out = resp.getWriter();	
			){
				mapper.writeValue(out, resultMap);
			}
		}else {
			resp.sendError(status);
		}
	}
}
