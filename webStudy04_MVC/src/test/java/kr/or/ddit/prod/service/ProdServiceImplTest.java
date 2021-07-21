package kr.or.ddit.prod.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.commons.exception.DataNotFoundException;
import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.prod.dao.ProdDAO;
import kr.or.ddit.prod.dao.ProdDAOImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

public class ProdServiceImplTest {
	
	private ProdDAO dao;
	private ProdService service;
	
	@Before
	public void setUp() throws Exception {
		dao = ProdDAOImpl.getInstance();
		service = new ProdServiceImpl();
	}

	@Test
	public void testCreateProd() {
		fail("Not yet implemented");
	}

	@Test
	public void testRetrieveProdList() {
		PagingVO<ProdVO> pagingVO = new PagingVO<>();
		pagingVO.setCurrentPage(1); 
		List<ProdVO> prodList = dao.selectProdList(pagingVO); 
		assertNotNull(prodList.get(0)); 
		
	}

	@Test
	public void testRetrieveProd() {
		fail("Not yet implemented");
	}

	@Test
	public void testModifyProd() {
		ProdVO prod = new ProdVO();
		prod.setProdId("P102000008");
		prod.setProdName("김칫국티비");
		prod.setProdCost(200000);
		prod.setProdPrice(300000); 
		prod.setProdSale(250000);
		prod.setProdOutline("평면김칫국의 기적");
		prod.setProdImg("kimch.jpg");
		prod.setProdTotalstock(10); 
		prod.setProdProperstock(5);  
		ServiceResult result = service.modifyProd(prod);
		assertEquals(ServiceResult.OK, result);
	}

}
