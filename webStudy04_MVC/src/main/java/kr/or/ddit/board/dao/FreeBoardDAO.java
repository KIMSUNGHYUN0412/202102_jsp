package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.vo.FreeBoardVO;
import kr.or.ddit.vo.PagingVO;

/**
 *	FreeBoard 테이블을 대상으로 한 Persistence Layer
 *
 */
public interface FreeBoardDAO { 

	/**
	 * 게시글 등록
	 * @param board PK를 제외한 나머지 상품 데이터를 가진 VO
	 * @param sqlSession TODO
	 * @return > 0 성공 , PK는 call by reference로 확인. 
	 */
	public int insertBoard(FreeBoardVO board, SqlSession sqlSession);
	
	/**
	 * totalRecord조회
	 * @param pagingVO
	 * @return totalRecord 수
	 */
	public int selectTotalRecoard(PagingVO<FreeBoardVO> pagingVO);
	
	/**
	 * 페이징처리된 게시글 목록 조회
	 * @param pagingVO
	 * @return 없으면 size==0
	 */
	public List<FreeBoardVO> selectBoardList(PagingVO<FreeBoardVO> pagingVO);
	
	/**
	 * 게시글 상세 조회
	 * @param boNo 게시글 번호
	 * @return 게시글 정보가 담긴 VO
	 */
	public FreeBoardVO selectBoard(int boNo); 
	
	/**
	 * 게시글 수정
	 * @param board 수정 데이터가 담긴 VO
	 * @param sqlSession TODO
	 * @return > 0 성공
	 */
	public int updateBoard(FreeBoardVO board, SqlSession sqlSession);
	
	/**
	 * 게시글 삭제
	 * @param boNo 게시글 번호
	 * @param sqlSession TODO
	 * @return > 0 성공
	 */
	public int deleteBoard(int boNo, SqlSession sqlSession);
	
	/**
	 * 조회수 증가
	 * @param boNo
	 * @return 
	 */
	public int incrementHit(int boNo);
	
	
	/**
	 * 추천수 증가
	 * @param boNo
	 * @return
	 */
	public int incrementRec(int boNo);
	
	
	/** 
	 * 신고수 증가
	 * @param boNo
	 * @return
	 */
	public int incrementRep(int boNo);
	
	
}
