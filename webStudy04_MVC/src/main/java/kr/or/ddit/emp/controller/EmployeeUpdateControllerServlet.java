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

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.commons.exception.DataNotFoundException;
import kr.or.ddit.emp.service.EmployeeService;
import kr.or.ddit.emp.service.EmployeeServiceImpl;
import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.vo.EmployeeVO;

@WebServlet("/employee/updateEmp.do")
public class EmployeeUpdateControllerServlet extends HttpServlet{
	private EmployeeService service = EmployeeServiceImpl.getInstance();
	
	private boolean validate(EmployeeVO employee, Map<String, String> errors) {
		boolean valid = true;
		// 검증 수행
		return valid;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		EmployeeVO employee = mapper.readValue(req.getInputStream(), EmployeeVO.class);
		Map<String, String> errors = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		int status = 200;
		ServiceResult result = null;
		if(validate(employee, errors)) {
			try {
				result = service.modifyEmployee(employee);
				if(ServiceResult.FAIL.equals(result)) {
					resultMap.put("message", "서버 오류 쫌따 다시 실행");
				}else {
					resultMap.put("success", true);
				}
			}catch (DataNotFoundException e) {
				status = 404;
			}
		}else {
			resultMap.put("message", "요청 검증을 통과하지 못함.");
			resultMap.put("errors", errors);
		}
		if(status==200){
			resp.setContentType("application/json;charset=UTF-8");
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
