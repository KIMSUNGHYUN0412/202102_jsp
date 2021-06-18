package kr.or.ddit.servlet05;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.jni.Local;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.enumtype.MimeType;

@WebServlet("/05/messageServiceWithLocale")
public class MessageServiceServletWithLocale extends HttpServlet{

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Locale locale = req.getLocale();
		req.setCharacterEncoding("utf-8");
		String paramlang = req.getParameter("lang"); 
		if(paramlang!=null && !paramlang.isEmpty()) {
			locale = Locale.forLanguageTag(paramlang); 
		} 
		//"kr.or.ddit.servlet05.message" -> basename은 _전까지다. 지금은 basename이 하나이다.
		ResourceBundle bundle = ResourceBundle.getBundle("kr.or.ddit.servlet05.message", locale);
		Map<String, Object> bundleMap = new HashMap<>(); 
		if(bundle.getKeys().hasMoreElements()) { 
			String key  = bundle.getKeys().nextElement(); 
			bundleMap.put(key, bundle.getObject(key));
		}
		  
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
