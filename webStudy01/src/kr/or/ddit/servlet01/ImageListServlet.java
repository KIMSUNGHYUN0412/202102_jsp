package kr.or.ddit.servlet01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageListServlet extends HttpServlet{
	String contentsPath;
	ServletContext application; 
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		application = getServletContext();
		contentsPath = application.getInitParameter("contentsPath");
		System.out.printf("%s 서블릿 초기화\n", getClass().getName()); 
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//응답데이터의 mime설정은 출력스트림 개방전에 해야함
		resp.setContentType("text/html; charset=utf-8");
		
		File contentFolder = new File(contentsPath);
		String[] imageList = contentFolder.list(new FilenameFilter() {
			
			@Override  // MIME 
			public boolean accept(File dir, String name) {
				//mimeText : main/sub;charset=encoding
				String mimeText = application.getMimeType(name);
				return mimeText!=null && mimeText.startsWith("image/"); 
			} 
		});
		
		String pattern = "<option>%s</option>";
		/*StringBuffer 힙에 저장.쓰고버릴수있음. 메모리 절약*/
		StringBuffer options = new StringBuffer();
		for(String name : imageList) {
			options.append(String.format(pattern, name));
		}
		
		InputStream is = getClass().getResourceAsStream("imageList.tmpl"); //내부적으로 클래스로더 사용 //contextPath아래 있는 클래스
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String tmp = null; 
		StringBuffer tmplSource = new StringBuffer();
		while((tmp = reader.readLine())!=null) {
			tmplSource.append(tmp+"\n");
		}
		String html = tmplSource.toString().replace("#{data}", options);
		resp.getWriter().println(html);
		
	}
}
