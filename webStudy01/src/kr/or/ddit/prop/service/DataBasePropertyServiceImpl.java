package kr.or.ddit.prop.service;

import java.util.Calendar;
import java.util.List;

import kr.or.ddit.prop.dao.DataBasePropertyDAO;
import kr.or.ddit.prop.dao.DataBasePropertyDAOImpl;
import kr.or.ddit.vo.DataBasePropertyVO;

public class DataBasePropertyServiceImpl implements DataBasePropertyService{ 
	private DataBasePropertyDAO dao = new DataBasePropertyDAOImpl();
	
	@Override
	public List<DataBasePropertyVO> retrieveDataBaseProperties(DataBasePropertyVO param) {
		//raw data
		List<DataBasePropertyVO> propList = dao.selectDataBasePropertyList(param);
		// logic -> information (데이터가공)
		Calendar cal = Calendar.getInstance();
		String pattern = "%s, %tc";
		for(DataBasePropertyVO prop : propList) {
			String infoValue = String.format(pattern, prop.getProperty_value(), cal);
			prop.setProperty_value(infoValue); 
		} 
		return propList;
	}
}
