package kr.or.ddit.various.props;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertiesReadView {
	public static void main(String[] args) {
		ConfigurableApplicationContext parent = new ClassPathXmlApplicationContext("kr/or/ddit/various/Various-DI.xml");
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"kr/or/ddit/various/props/Properties-read.xml"}, parent);
		context.registerShutdownHook();
//		DataBaseInfoPropVO infoVO1 = context.getBean("infoVO1",DataBaseInfoPropVO.class);
//		DataBaseInfoPropVO infoVO2 = context.getBean("infoVO2",DataBaseInfoPropVO.class);
//		log.info("{}", infoVO1);
//		log.info("{}", infoVO2); 
	}
}
