package kr.or.ddit.listener;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.vo.MemberVO;

/**
 * Application Lifecycle Listener implementation class CustomSessionListener
 *
 */
@WebListener
public class CustomSessionListener implements HttpSessionListener , HttpSessionAttributeListener{
	private static final Logger logger = LoggerFactory.getLogger(CustomSessionListener.class);
	
    public void sessionCreated(HttpSessionEvent se)  {   
    	ServletContext application = se.getSession().getServletContext();
    	Integer userCount =  (Integer) application.getAttribute("userCount");
    	userCount++;
    	Integer currentUserCount =  (Integer) application.getAttribute("currentUserCount");
    	currentUserCount++; 
    	
    	application.setAttribute("userCount", userCount);
    	application.setAttribute("currentUserCount", currentUserCount);
    } 
  
    
    public void sessionDestroyed(HttpSessionEvent se)  {  
    	ServletContext application = se.getSession().getServletContext();
    	Integer currentUserCount =  (Integer) application.getAttribute("currentUserCount");
    	if(currentUserCount > 0) {
    		currentUserCount--; 
    		application.setAttribute("currentUserCount", currentUserCount);  
    	} 
    }


    
    
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
//		ServletContext application = event.getSession().getServletContext();
//		String attrName = event.getName();
//		if(!"authMember".equals(attrName)) return;
//		Map<String, MemberVO> currentUserList = (Map)application.getAttribute("currentUserList");
//		MemberVO authMember = (MemberVO) event.getValue();
//		currentUserList.put(authMember.getMemId(), authMember);
	}


	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
//		ServletContext application = event.getSession().getServletContext();
//		String attrName = event.getName();
//		if(!"authMember".equals(attrName)) return;
//		Map<String, MemberVO> currentUserList = (Map)application.getAttribute("currentUserList");
//		MemberVO authMember = (MemberVO) event.getValue();
//		currentUserList.remove(authMember.getMemId());
	}


	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		
	}
	
}
