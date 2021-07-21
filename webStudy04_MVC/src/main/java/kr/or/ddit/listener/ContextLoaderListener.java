package kr.or.ddit.listener;

import java.io.File;
import java.util.LinkedHashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.vo.MemberVO;

/**
 *	server side application 에서의 이벤트 처리
 *	1. 이벤트의 타켓 결정 : web application
 *	2. 이벤트의 종류 결정
 *		request lifecycle, session lifecycle, application lifecycle
 *		scope 를 대상으로 attribute add/replace/remove
 *	3. 이벤트 핸들러 구현 : Listener 구현
 *	4. 이벤트 타켓에 핸들러 연결 : web.xml에서 핸들러 등록 및 부착
 *
 */
public class ContextLoaderListener implements ServletContextListener{
	private static final Logger logger = LoggerFactory.getLogger(ContextLoaderListener.class);
	
	public static File prodImages;
	public static String prodImagesUrl;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext application = sce.getServletContext();
		application.setAttribute("cPath", application.getContextPath()); 
		prodImagesUrl = "/resources/prodImages";
		application.setAttribute("prodImagesUrl", prodImagesUrl);  
		String saveFolderPath = application.getRealPath(prodImagesUrl);
		prodImages = new File(saveFolderPath);
		
		application.setAttribute("userCount", new Integer(0));
		application.setAttribute("currentUserCount", new Integer(0));
		
		//중복 로그인 비허용 | 접속자의 정보 활용
		application.setAttribute("currentUserList", new LinkedHashMap<String, MemberVO>());
		
		if(!prodImages.exists()) prodImages.mkdirs();
		logger.info("{} 어플리케이션 초기화", application.getContextPath());
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
