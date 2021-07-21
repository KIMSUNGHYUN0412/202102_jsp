package kr.or.ddit.emp.dao;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.vo.EmployeeVO;

public class EmployeeDAOImplTest {

	EmployeeDAO empDAO;
	Map<String, Object> pMap;
	
	@Before
	public void setUp() throws Exception {
		empDAO = EmployeeDAOImpl.getInstance();
		pMap = new HashMap<>();
	}

	@Test
	public void testInsertEmployee() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectEmployeeList() {
		List<EmployeeVO> empList = empDAO.selectEmployeeList(pMap);
		assertNotNull(empList); 
		assertEquals(1, empList.size());  
		assertEquals(false, empList.get(0).isLeaf());
		System.out.println(empList.get(0));
	}

	@Test
	public void testSelectEmployee() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateEmployee() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteEmployee() {
		fail("Not yet implemented");
	}

}
