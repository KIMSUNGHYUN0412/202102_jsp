package kr.or.ddit.ioc.sample.service;

import java.util.Date;

import kr.or.ddit.ioc.sample.dao.ISampleDAO;
import kr.or.ddit.ioc.sample.dao.SampleDAOFactory;
import kr.or.ddit.ioc.sample.dao.SampleDAOImpl_Mysql;
import kr.or.ddit.ioc.sample.dao.SampleDAOImpl_Oracle;

public class SampleServiceImpl implements ISampleService {

//	1. new 키워드로 의존 객체 직접 생성 - 결합력 최상 (망한코드ㅋ.ㅋ)
//	private ISampleDAO dao = new SampleDAOImpl_Oracle();
	
//	2. Factory Object Pattern 활용 - Factory와의 결합력 잔존
//	private ISampleDAO dao = new SampleDAOFactory().getSampleDAO();
	
//	3. Strategy Pattern(DI) 전략패턴 활용 - 내부 전략주입자 (결합력 이동) 
//		- setter injection : setter호출 안정성 문제
//		- constructor injection
	private ISampleDAO dao;
	
	
	public SampleServiceImpl() {
		super();
	}

	public SampleServiceImpl(ISampleDAO dao) {
		super();
		this.dao = dao;
	}

	public void setDaoasdf(ISampleDAO dao) {
		this.dao = dao;
	}
	
	public void init() { 
		System.out.println(getClass().getSimpleName()+ " 초기화, injected dao : " + dao);
	}

	public void destroy() {
		System.out.println(getClass().getSimpleName()+ " 소멸");
	} 
			
	@Override
	public StringBuffer retrieveById(String pk) {
		String rawData = dao.selectById(pk);
		StringBuffer content = new StringBuffer();
		content.append(rawData);
		content.append("\n" + new Date() + " 에 가공된 컨텐츠");
		return content;
	}

}
