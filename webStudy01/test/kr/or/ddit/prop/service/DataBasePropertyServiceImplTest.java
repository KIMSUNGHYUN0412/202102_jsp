package kr.or.ddit.prop.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import kr.or.ddit.prop.dao.DataBasePropertyDAOImpl;
import kr.or.ddit.vo.DataBasePropertyVO;

public class DataBasePropertyServiceImplTest {

	@Test
	public void testRetrieveDataBaseProperties() {
		List<DataBasePropertyVO> list = new DataBasePropertyServiceImpl().retrieveDataBaseProperties();
		assertNotNull(list); 
	}

}
