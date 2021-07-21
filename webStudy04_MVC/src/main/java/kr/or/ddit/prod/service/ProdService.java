package kr.or.ddit.prod.service;

import java.util.List;

import kr.or.ddit.commons.exception.DataNotFoundException;
import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

/**
 * 상품 관리(CRUD) Business Logic Layer(Service Layer)
 *
 */
public interface ProdService {
	
	/**
	 * 신규 상품 등록 
	 * @param prod PK 입력 받지 않음
	 * @return OK : 성공시 call by reference로 PK 조회 가능, FAIL
	 */
	public ServiceResult createProd(ProdVO prod);
	
	/**
	 * 페이징처리된 상품 목록 조회
	 * @param pagingVO call by reference 로 dataList 와 totalRecord 세팅
	 */
	public void retrieveProdList(PagingVO<ProdVO> pagingVO);
	
	/**
	 * 상품 상세 조회
	 * @param prodId
	 * @return 존재하지 않으면 {@link DataNotFoundException}
	 */
	public ProdVO retrieveProd(String prodId);
	
	
	/**
	 * 상품 정보 수정
	 * @param prod
	 * @return 존재하지 않으면 {@link DataNotFoundException}, OK, FAIL
	 */
	public ServiceResult modifyProd(ProdVO prod);
}
