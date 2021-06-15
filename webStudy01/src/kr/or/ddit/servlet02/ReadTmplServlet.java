package kr.or.ddit.servlet02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ReadTmplServlet extends HttpServlet{
	ServletContext application;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		application = getServletContext();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		StringBuffer template = readTemplate(req);
		makeData(req);
		String mime = getMime();
		resp.setContentType(mime); 
		makeResponseContents(template, req, resp);
	}

	protected abstract String getMime();
	
	//1. tmpl읽기
	private StringBuffer readTemplate(HttpServletRequest req) throws IOException{
		StringBuffer template = null;
		String tmplPath = req.getServletPath();
		InputStream is = application.getResourceAsStream(tmplPath);
		if(is!=null) {
			try(  //trywithresource구문 close()해야하는 객체 초기화. finally 자동 생성. 1.7에서 나온 문법
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));
					) {
				template = new StringBuffer();
				String tmp = null;
				while((tmp = reader.readLine())!=null) {
					template.append(String.format("%s\n", tmp));
				}
			} 
		}
		return template;
	}
	//2. 데이터 만들기(**)
	protected abstract void makeData(HttpServletRequest req);
 
	//3. 실제 데이터로 구멍을 치환
	//4. 컨텐츠로 응답 전송
	private void makeResponseContents(StringBuffer template, HttpServletRequest req, HttpServletResponse resp) 
			throws IOException{
		if(template==null) return;
		//응답데이터 확정여부
		if(resp.isCommitted()) return;
		String tmplSrc = template.toString();
		//#{식별자}
		Pattern regex = Pattern.compile("#\\{([\\w_]+)\\}");
		Matcher matcher = regex.matcher(tmplSrc);
		StringBuffer html = new StringBuffer();
		while(matcher.find()) {
			String name = matcher.group(1);
			Object data = req.getAttribute(name); 
			//Objects : 1.7에서 나온 object메서드 보충판..? null safe
			matcher.appendReplacement(html, Objects.toString(data, ""));
		}
		matcher.appendTail(html);

		try(
				PrintWriter out = resp.getWriter();
				){

			out.println(html);
		}
		//		#{test1}, #{test2}
		//		req.getAttribute("test1")
		//		req.getAttribute("test2")
	}
}
