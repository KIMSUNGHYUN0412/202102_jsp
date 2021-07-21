package kr.or.ddit.servlet05;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.enumtype.MimeType;

@WebServlet("/05/messageService")
public class MessageServiceServlet extends HttpServlet{
	ResourceBundle bundle; 
	Map<String, Object> bundleMap;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		String basename = config.getInitParameter("basename");
		bundle = ResourceBundle.getBundle("kr.or.ddit.servlet05.message");
		bundleMap = new HashMap<>(); 
		if(bundle.getKeys().hasMoreElements()) { 
			String key  = bundle.getKeys().nextElement();
			bundleMap.put(key, bundle.getObject(key));
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// ** mime 설정 : Content-Type
		String accept = req.getHeader("Accept");
		MimeType mime = MimeType.findMimeType(accept);
		 
		if(!MimeType.JSON.equals(mime)) { 
			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE); 
			return;
		}
		
		// 1.marshalling
		
		ObjectMapper mapper = new ObjectMapper();
//		String json = mapper.writeValueAsString(bundleMap);
//		System.out.println(json);
		
	
		resp.setContentType(mime.getMimeText());
		// 2.직렬화
		try(
			PrintWriter out = resp.getWriter();
		){ 
//			out.println(json); 
			mapper.writeValue(out, bundleMap); 
		}
	}
}
