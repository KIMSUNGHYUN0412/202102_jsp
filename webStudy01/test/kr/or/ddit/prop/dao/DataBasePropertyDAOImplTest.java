package kr.or.ddit.prop.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import kr.or.ddit.vo.DataBasePropertyVO;

public class DataBasePropertyDAOImplTest {

	@Test
	public void testSelectDataBasePropertyList() {
		List<DataBasePropertyVO> list = new DataBasePropertyDAOImpl().selectDataBasePropertyList();
		assertNotNull(list); 
	}

}
