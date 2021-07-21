package kr.or.ddit.prop.service;

import java.util.List;

import kr.or.ddit.prop.dao.DataBasePropertyDAO;
import kr.or.ddit.vo.DataBasePropertyVO;
import kr.or.ddit.vo.PagingVO;
 
/**
 * Business Logic Layer : raw 데이터 가공 logic을 운영하기 위한 계층.
 * 
 *
 */
public interface DataBasePropertyService {
	
//	public int retrieveDBPropCount(PagingVO<DataBasePropertyVO> pagingVO);
	
	public List<DataBasePropertyVO> retrieveDataBaseProperties(PagingVO<DataBasePropertyVO> pagingVO);
} 
