package kr.or.ddit.member.dao;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.emp.dao.EmployeeDAO;
import kr.or.ddit.emp.dao.EmployeeDAOImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;

public class MemberDAOImplTest {

	
	MemberDAO dao;
	
	@Before
	public void setUp() throws Exception {
		dao = MemberDAOImpl.getInstance();
	}

	@Test
	public void testSelectMemberById() {
		MemberVO member = dao.selectMemberById("a001");
		System.out.println(member);
		assertNotNull(member);  
	}

	@Test
	public void testInsertMember() {
		fail("Not yet implemented"); 
	}
//
//	@Test
//	public void testSelectMemberList() {
//		List<MemberVO> memberList = dao.selectMemberList(PagingVO<MemberVO>);
//		assertNotNull(memberList); 
//		assertEquals(25, memberList.size());
//		
//	} 
	
 
	@Test
	public void testSelectMemberDetail() {
		MemberVO member = dao.selectMemberDetail("a001");
		assertNotNull(member);    
	} 
 
	@Test
	public void testUpdateMember() { 
		MemberVO member = dao.selectMemberDetail("a001");
		member.setMemName("김은대"); 
		int rowcnt = dao.updateMember(member);
		assertEquals(1, rowcnt);
	}
	
	@Test
	public void testDeleteMember() {
		int rowcnt = dao.deleteMember("a001");
		assertEquals(1, rowcnt); 
	}

}
