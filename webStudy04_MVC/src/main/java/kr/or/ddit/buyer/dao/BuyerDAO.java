package kr.or.ddit.buyer.dao;

import java.util.List;

import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;

/**
 *  거래처 관리(CRUD) Persistence Layer
 *
 */
public interface BuyerDAO {
//	alt + shift + j => 주석 탬플릿
	/**
	 * 신규 거래처 등록 
	 * @param buyer PK(buyerID)를 제외한 거래처 데이터를 가진 VO
	 * @return rowcnt > 0 성공, PK는 call by reference로 확인
	 */
	public int insertBuyer(BuyerVO buyer);
	
	/**
	 * totalRecord조회
	 * @param pagingVO
	 * @return totalRecord 개수
	 */
	public int selectTotalRecord(PagingVO<BuyerVO> pagingVO);
	
	/**
	 * 페이징 처리된 거래처 목록 조회
	 * @param pagingVO
	 * @return 존재하지 않으면 size == 0 
	 */
	public List<BuyerVO> selectBuyerList(PagingVO<BuyerVO> pagingVO);
	
	/**
	 * 거래처 상세 조회(거래상품목록 동시 조회)
	 * @param BuyerId
	 * @return
	 */
	public BuyerVO selectBuyer(String buyerId);
	
	/**
	 * 거래처 정보 수정
	 * @param buyer
	 * @return rowcnt > 0 성공
	 */
	public int updateBuyer(BuyerVO buyer);
}
