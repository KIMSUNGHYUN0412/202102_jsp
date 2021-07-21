package kr.or.ddit.ioc.sample.view;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import kr.or.ddit.ioc.sample.dao.ISampleDAO;
import kr.or.ddit.ioc.sample.dao.SampleDAOImpl_Mysql;
import kr.or.ddit.ioc.sample.dao.SampleDAOImpl_Oracle;
import kr.or.ddit.ioc.sample.service.ISampleService;
import kr.or.ddit.ioc.sample.service.SampleServiceImpl;

public class SampleView {
	public static void main(String[] args) {
//		//3. 전략패턴 - 전략의 주입자 역할 - 결합력이 옮겨짐
//		ISampleDAO dao = new SampleDAOImpl_Mysql(); 
//		ISampleService service = new SampleServiceImpl(dao);
		
		ApplicationContext container = new ClassPathXmlApplicationContext("kr/or/ddit/ioc/sample/conf/Sample-Context.xml");
		ISampleService service = container.getBean(SampleServiceImpl.class);
		String pk = "a001"; 
		StringBuffer result = service.retrieveById(pk);
		System.out.println(result);
	}
}
