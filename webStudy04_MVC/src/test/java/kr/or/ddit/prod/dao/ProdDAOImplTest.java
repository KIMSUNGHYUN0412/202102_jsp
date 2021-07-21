package kr.or.ddit.prod.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.ddit.vo.ProdVO;

public class ProdDAOImplTest {
	
	private ProdDAOImpl dao;
	Logger logger = LoggerFactory.getLogger(ProdDAOImplTest.class);
	
	@Before
	public void setUp() throws Exception {
		dao = ProdDAOImpl.getInstance(); 
	}

	@Test
	public void testSelectProd() {
		ProdVO prod = dao.selectProd("P101000001"); 
		assertNotNull(prod); 
		if(logger.isInfoEnabled()) {
			logger.info("prod : {}", prod);
		} 
		assertNotNull(prod.getBuyer()); 
		if(logger.isInfoEnabled()) {
			logger.info("buyer : {}", prod.getBuyer());
		} 
		assertEquals(2, prod.getMemberList().size());
		if(logger.isInfoEnabled()) {
			logger.info("MemberList : {}",prod.getMemberList());
		} 
	}
	
//	@Test
//	public void testInsertProd() {
//		ProdVO prod = new ProdVO();
//		prod.setProdName("김칫국");
//		prod.setProdLgu("P101");
//		prod.setProdBuyer("P10101");
//		prod.setProdCost(20000);
//		prod.setProdPrice(30000); 
//		prod.setProdSale(25000);
//		prod.setProdOutline("평면김칫국의 기적");
//		prod.setProdImg("kimch.jpg");
//		prod.setProdTotalstock(5);
//		prod.setProdProperstock(10); 
//		int rowcnt = dao.insertProd(prod, sqlSession);
//		assertEquals(1, rowcnt);
//		
//	}
//	
//	@Test
//	public void testUpdateProd() {
//		ProdVO prod = new ProdVO();
//		prod.setProdId("P102000008");
//		prod.setProdName("김칫국티비");
//		prod.setProdCost(200000);
//		prod.setProdPrice(300000); 
//		prod.setProdSale(250000);
//		prod.setProdOutline("평면김칫국의 기적");
//		prod.setProdImg("kimch.jpg");
//		prod.setProdTotalstock(10); 
//		prod.setProdProperstock(5); 
//		int rowcnt = dao.updateProd(prod, sqlSession);
//		assertEquals(1, rowcnt); 
//	}
//	
}
