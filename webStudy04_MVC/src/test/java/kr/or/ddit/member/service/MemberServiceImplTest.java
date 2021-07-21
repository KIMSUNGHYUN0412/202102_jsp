package kr.or.ddit.member.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.commons.UserNotFoundException;
import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.member.dao.MemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.vo.MemberVO;

public class MemberServiceImplTest {
	private MemberService service;
	@Before
	public void setUp() throws Exception {
		service = MemberServiceImpl.getInstance();
	}

	@Test
	public void testCreateMember() {
 		MemberVO insertMember = MemberVO.builder().memId("x001").build();
		ServiceResult result = service.createMember(insertMember);  
		assertEquals(ServiceResult.PKDUPLICATED, result);   
	} 
 
	@Test
	public void testRetrieveMemberList() {
		fail("Not yet implemented"); 
	} 

	@Test
	public void testRetrieveMember() {
		String mem_id = "o001";
		MemberVO member = service.retrieveMember(mem_id);
		assertNotNull(member);    
	} 

	@Test
	public void testModifyMember() {
		fail("Not yet implemented");
	}

	@Test(expected=UserNotFoundException.class)
	public void testRemoveMember1() {
		MemberVO member = MemberVO.builder()
					.memId("asdfasdf")
					.build(); 
		service.removeMember(member);
	} 
	
	@Test
	public void testRemoveMember() {
		MemberVO member = MemberVO.builder()
				.memId("a001")
				.memPass("4564656")
				.build(); 
		ServiceResult result = service.removeMember(member);
		assertEquals(ServiceResult.INVALIDPASSWORD, result); 
		
		member.setMemPass("asdfasdf");
		result = service.removeMember(member);
		assertEquals(ServiceResult.OK, result);
	}

}
