package kr.or.ddit.prod.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;


/**
 * 상품 관리(CRUD) Persistence Layer
 *
 */
public interface ProdDAO {
	
	/**
	 *  신규 상품 등록
	 * @param prod PK를 제외한 나머지 상품 데이터를 가진 VO
	 * @param sqlSession TODO
	 * @return rowcnt > 0 성공, PK는 call by reference로 확인.
	 */
	public int insertProd(ProdVO prod, SqlSession sqlSession);
	
	/**
	 * totalRecord 조회
	 * @param pagingVO
	 * @return totalRecord 수
	 */
	public int selectTotalRecord(PagingVO<ProdVO> pagingVO);
	
	/**
	 * 페이징 처리된 상품 목록 조회
	 * @param pagingVO
	 * @return 존재하지않으면 size == 0
	 */
	public List<ProdVO> selectProdList(PagingVO<ProdVO> pagingVO);
	
	/**
	 * 상품 상세 조회(구매자 목록 동시 조회)
	 * @param prodId
	 * @return
	 */
	public ProdVO selectProd(String prodId);
	
	/**
	 * 상품 정보 수정
	 * @param prod
	 * @param sqlSession TODO
	 * @return rowcnt > 0 성공
	 */
	public int updateProd(ProdVO prod, SqlSession sqlSession);
}
