package kr.or.ddit.ioc.sample.view;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import kr.or.ddit.ioc.sample.dao.ISampleDAO;
import kr.or.ddit.ioc.sample.service.ISampleService;
import kr.or.ddit.ioc.sample.service.SampleServiceImpl;

public class BeanContainerDescView {
	public static void main(String[] args) {
		ConfigurableApplicationContext container = new GenericXmlApplicationContext("classpath:kr/or/ddit/container/Bean-ContainerDesc.xml");
		//종료 조건이 되면 close호출
		container.registerShutdownHook();
//		ISampleService service1 = container.getBean("sampleService1", ISampleService.class);
//		ISampleService service2 = container.getBean("sampleService2", ISampleService.class);
		
//		String pk = "a001";
//		StringBuffer content = service1.retrieveById(pk);
//		System.out.println(content); 
		
		//singleton 대상 bean
//		ISampleDAO dao1 = container.getBean("sampleOracle",ISampleDAO.class);
//		ISampleDAO dao2 = container.getBean("sampleOracle",ISampleDAO.class); 
//		System.out.println(dao1==dao2);
//		System.out.println(service1==service2);
		
//		등록된 bean을 가비지컬렉션 대상으로 만듦. -> shut down
//		container.close();
	}
}
