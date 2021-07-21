package kr.or.ddit.prod.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.vo.BuyerVO;

public class OthersDAOImplTest {
	private OthersDAO othersDAO = new OthersDAOImpl();
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSelectLprodList() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectBuyerList() {
		List<BuyerVO> buyerList = othersDAO.selectBuyerList();
		assertNotNull(buyerList);
		
	}

}
