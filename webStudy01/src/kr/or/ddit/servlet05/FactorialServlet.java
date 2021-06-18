package kr.or.ddit.servlet05;

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

import kr.or.ddit.enumtype.MimeType;

@WebServlet("/05/factorial")
public class FactorialServlet extends HttpServlet{

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      
      String mime = req.getParameter("mime");
      int left = Integer.parseInt(req.getParameter("left"));
      
      System.out.println(mime);
      
      //결과 계산
      String res = "%d! = %d";
      int result = 1;
      for(int i = 1; i <= left; i++) {
         result = result * i; 
      }
      
      res = String.format(res, left, result);
      
      Map<String, Object> map = new HashMap<>();
      map.put("left", left);
      map.put("operator", "!");
      map.put("expression", res);
      
      //mime
      MimeType mimeType = MimeType.findMimeType(mime);
      
      //marshalling
      ObjectMapper mapper = new ObjectMapper();
      
      resp.setContentType(mimeType.getMimeText());
      try(PrintWriter out = resp.getWriter()){
         mapper.writeValue(out, map);
      }
      
   }
   
}