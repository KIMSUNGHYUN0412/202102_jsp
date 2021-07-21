package kr.or.ddit.ioc.sample.dao;

public class SampleDAOImpl_Oracle implements ISampleDAO {

	@Override
	public String selectById(String pk) {
		return "Oracle에서 가져온 " + pk + "에 해당하는 레코드";
	}
	
	public void init() {
		System.out.println(getClass().getSimpleName()+ " 초기화");
	}

	public void destroy() {
		System.out.println(getClass().getSimpleName()+ " 소멸");
	}
}
