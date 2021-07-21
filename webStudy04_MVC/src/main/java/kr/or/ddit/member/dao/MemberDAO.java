package kr.or.ddit.member.dao;

import java.util.List;

import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ZipVO;

/**
 * 회원관리(CRUD)를 위한 Persistence Layer 
 *
 */
public interface MemberDAO {
	/**
	 * 신규 회원 등록
	 * @param member
	 * @return > 0 : 성공 
	 */
	public int insertMember(MemberVO member);
	
	/**
	 * 페이징 처리를 위해 total record 조회
	 * @param pagingVO
	 * @return
	 */
	public int selectTotalRecord(PagingVO<MemberVO> pagingVO);
	
	/**
	 * 페이징 처리를 위해 구간별 회원 목록 조회
	 * @param pagingVO TODO
	 * @return 존재하지않을 경우 size==0
	 */
	public List<MemberVO> selectMemberList(PagingVO<MemberVO> pagingVO);
	
	/**
	 * 회원 상세 조회
	 * @param mem_id
	 * @return 조건에 맞는 회원이 없는 경우, null 반환
	 */
	public MemberVO selectMemberDetail(String mem_id);
	
	/**
	 * 식별자(PK)로 레코드 조회
	 * @param mem_id
	 * @return 가 존재하지 않는 경우 null 반환
	 */
	public MemberVO selectMemberById(String mem_id); 
	
	/**
	 * 회원 정보 수정
	 * @param member
	 * @return > 0 : 성공 
	 */
	public int updateMember(MemberVO member);
	
	/**
	 * 회원 정보 삭제
	 * @param mem_id
	 * @return > 0 : 성공 
	 */
	public int deleteMember(String mem_id);
	
	/**
	 * 우편번호 검색 목록 조회 
	 * @return
	 */
	public List<ZipVO> searhZipList();
}
