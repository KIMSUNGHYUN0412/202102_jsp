package kr.or.ddit.emp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.ddit.emp.service.EmployeeService;
import kr.or.ddit.emp.service.EmployeeServiceImpl;

@WebServlet("/employee/insertEmp.do")
public class EmployeeInsertControllerServlet extends HttpServlet {
	
	private EmployeeService service = EmployeeServiceImpl.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}

}
