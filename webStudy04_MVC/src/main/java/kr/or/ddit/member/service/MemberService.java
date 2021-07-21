package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.commons.UserNotFoundException;
import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ZipVO;

/**
 * 회원 관리(CRUD) Business Logic Layer
 *
 */
public interface MemberService {
	
	/**
	 *  신규 회원 등록
	 * @param member
	 * @return PKDUPICATED(중복아이디), OK, FAIL
	 */
	public ServiceResult createMember(MemberVO member);
	
	
	public int retrieveMemberCount(PagingVO<MemberVO> pagingVO);
	
	/** 
	 * 회원 목록 조회
	 * @param pagingVO TODO
	 * @return 존재하지않을 경우 size==0
	 */
	public List<MemberVO> retrieveMemberList(PagingVO<MemberVO> pagingVO);
	
	/**
	 * 회원 상세 조회
	 * @param mem_id
	 * @return 존재하지않는 경우 {@link UserNotFoundException} 발생
	 */
	public MemberVO retrieveMember(String mem_id);
	
	/**
	 * 회원 정보 수정
	 * @param member
	 * @return 존재하지않는 경우 {@link UserNotFoundException} 발생
	 * 			INVALIDPASSWORD, OK, FAIL 
	 */
	public ServiceResult modifyMember(MemberVO member);
	
	/**
	 * 회원 정보 삭제 
	 * @param member
	 * @return 존재하지않는 경우 {@link UserNotFoundException} 발생
	 * 			INVALIDPASSWORD, OK, FAIL 
	 */
	public ServiceResult removeMember(MemberVO member);
	
	/**
	 * 우편검색 목록 조회
	 * @return  존재하지않을 경우 size==0
	 */ 
	public List<ZipVO> retrieveZipList();
}
