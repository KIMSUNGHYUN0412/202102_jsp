package kr.or.ddit.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Front로부터 받은 logical viewName에 따라 실제 뷰로 이동하기 위한 코드를 가짐. 
 * logical viewName이 "redirect:"으로 시작하면, redirection.
 *
 */
public interface ViewResolver {
	public void viewResolve(String viewName, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
