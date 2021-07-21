package kr.or.ddit.buyer.service;

import kr.or.ddit.commons.exception.DataNotFoundException;
import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;

/**
 * 거래처 관리(CRUD) Business Logic Layer
 *
 */
public interface BuyerService {
	
	/**
	 * 신규 거래처 등록
	 * @param buyer PK 입력받지 않음
	 * @return OK: 성공시 call by reference로 PK 조회 가능, FAIL
	 */ 
	public ServiceResult createBuyer(BuyerVO buyer);
	
	/**
	 * 페이징 처리된 상품 목록 조회
	 * @param pagingVO call by reference 로 dataList와 totalRecord 세팅
	 */
	public void retrieveBuyerList(PagingVO<BuyerVO> pagingVO);
	
	
	/**
	 * 거래처 상세 조회
	 * @param buyerId
	 * @return 존재하지 않으면 {@link DataNotFoundException}
	 */
	public BuyerVO retrieveBuyer(String buyerId);

	/**
	 * 거래처 정보 수정
	 * @param buyer
	 * @return 존재하지 않으면 {@link DataNotFoundException}, OK, FAIL
	 */ 
	public ServiceResult modifyBuyer(BuyerVO buyer);
}
