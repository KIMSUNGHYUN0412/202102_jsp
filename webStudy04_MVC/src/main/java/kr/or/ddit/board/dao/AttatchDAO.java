package kr.or.ddit.board.dao;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.vo.AttatchVO;
import kr.or.ddit.vo.FreeBoardVO;

/**
 * Attatch 테이블을 대항으로 한 Persistence Layer
 *
 */
public interface AttatchDAO {
	/**
	 * 하나의 게시글과 관련된 첨부파일들을 모두 한번에 insert
	 * @param board boNo활용
	 * @param sqlSession TODO
	 * @return
	 */
	public int insertAttatches(FreeBoardVO board, SqlSession sqlSession);
	
	/**
	 * 다운로드를 위한 첨부파일 검색
	 * @param attNo 
	 * @return 파일의 이진데이터 포함한 AttatchVO
	 */ 
	public AttatchVO selectAttatch(int attNo);
	
	/** 
	 * 하나의 게시글에 포함된 첨부파일 삭제
	 * @param board
	 * @param sqlSession TODO
	 * @return
	 */
	public int deleteAttatches(FreeBoardVO board, SqlSession sqlSession);
	
	/**
	 * 게시글 삭제 시 포함된 첨부파일 전체 삭제
	 * @param boNo 삭제하고자하는 게시글번호
	 * @param sqlSession TODO
	 * @return
	 */
	public int deleteAll(int boNo, SqlSession sqlSession);
	
	
	/**
	 * 다운로드수 증가
	 * @param attNo
	 * @param sqlSession  
	 * @return
	 */ 
	public int incrementDownCount(int attNo);
}
