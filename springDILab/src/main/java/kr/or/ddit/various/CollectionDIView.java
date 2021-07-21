package kr.or.ddit.various;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class CollectionDIView {
	public static void main(String[] args) {
		ConfigurableApplicationContext container = new GenericXmlApplicationContext("classpath:kr/or/ddit/various/Collection-DI.xml");
		container.registerShutdownHook();
		
	}
}
