package kr.or.ddit.various;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class VariousDIView {
	public static void main(String[] args) {
		// constructor inject
		// 주입받지않으면 bean등록 X
		// 주입받을때마다 새로운 객체가 만들어지도록
		// property에 값이 제대로 들어갔는지 주입 후에 log를 통해 확인 
		// 컨테이너 객체 사용후 shut down - bean 소멸 확인 
		ConfigurableApplicationContext container = new GenericXmlApplicationContext("classpath:kr/or/ddit/various/Various-DI.xml");
		container.registerShutdownHook();
		
		VariousDIVO vo1 = container.getBean("vo1", VariousDIVO.class); 
		VariousDIVO vo2 = container.getBean("vo1", VariousDIVO.class); 
		System.out.println(vo1==vo2);
		
		//setter inject
		//바로생성 
		//싱글톤
		//vo1이 먼저 생성 
		VariousDIVO vo3 = container.getBean("vo2", VariousDIVO.class);
		VariousDIVO vo4 = container.getBean("vo2", VariousDIVO.class);
		System.out.println(vo3==vo4);
	}
}
