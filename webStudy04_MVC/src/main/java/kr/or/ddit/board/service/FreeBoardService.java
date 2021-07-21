package kr.or.ddit.board.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.commons.exception.DataNotFoundException;
import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.vo.AttatchVO;
import kr.or.ddit.vo.FreeBoardVO;
import kr.or.ddit.vo.PagingVO;

public interface FreeBoardService {
	/**
	 * 게시글 등록
	 * @param board PK 입력 받지 않음
	 * @param sqlSession TODO
	 * @return OK: 성공시 call by reference로 PK 조회 가능, FAIL
	 */
	public ServiceResult createBoard(FreeBoardVO board);
	 
	/**
	 * totalRecord 조회
	 * @param pagingVO
	 * @return tottalRecord 수
	 */
	public int retrieveBoardCount(PagingVO<FreeBoardVO> pagingVO);
	
	/**
	 * 페이징처리된 게시글 목록 조회
	 * @param pagingVO
	 * @return 없으면 size==0
	 */
	public List<FreeBoardVO> retrieveBoardList(PagingVO<FreeBoardVO> pagingVO);
	
	/**
	 * 게시글 상세 조회
	 * @param boNo 게시글 번호
	 * @return 없으면 {@link DataNotFoundException}
	 */
	public FreeBoardVO retrieveBoard(int boNo);
	
	/**
	 * 게시글 수정
	 * @param board boPass 확인
	 * @return {@link DataNotFoundException}, INVALIDPASSWORD, OK, FAIL
	 */
	public ServiceResult modifyBoard(FreeBoardVO board);
	
	/**
	 * 게시글 삭제
	 * @param board boPass 확인
	 * @return {@link DataNotFoundException}, INVALIDPASSWORD, OK, FAIL
	 */
	public ServiceResult removeBoard(FreeBoardVO board);
	
	/**
	 * 첨부파일 다운로드
	 * @param attNo
	 * @return attSavename이 포함된 AttatchVO
	 */
	public AttatchVO download(int attNo);
	
	public static enum CountType{RECOMMEND, REPORT}
	/**
	 * 신고수 or 추천수 증가
	 * @param boNo 
	 * @param type
	 * @return {@link DataNotFoundException}, OK, FAIL
	 */
	public ServiceResult incrementCount(int boNo, CountType type);
}
