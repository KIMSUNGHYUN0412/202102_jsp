package kr.or.ddit.prop.service;

import java.util.List;

import kr.or.ddit.vo.DataBasePropertyVO;
 
/**
 * Business Logic Layer : raw 데이터 가공 logic을 운영하기 위한 계층.
 * 
 *
 */
public interface DataBasePropertyService {
	public List<DataBasePropertyVO> retrieveDataBaseProperties(DataBasePropertyVO param);
}
