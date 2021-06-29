package kr.or.ddit.prop.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.db.ConnectionFactory;
import kr.or.ddit.vo.DataBasePropertyVO;

public class DataBasePropertyDAOImpl implements DataBasePropertyDAO {

	@Override
	public List<DataBasePropertyVO> selectDataBasePropertyList() {
		try(
				Connection conn = ConnectionFactory.getConnection(); //커넥션객체 닫으면 resultset도 자동으로 닫힘 
				Statement stmt = conn.createStatement();
			){
//		 	4. 쿼리 객체 생성   
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT PROPERTY_NAME, PROPERTY_VALUE, DESCRIPTION "); 
				sql.append(" FROM DATABASE_PROPERTIES");  
//		 	5. 쿼리 실행
				ResultSet rs = stmt.executeQuery(sql.toString());
				List<DataBasePropertyVO> propList = new ArrayList<>();
				while(rs.next()){
					DataBasePropertyVO rowVO = new DataBasePropertyVO(); 
					rowVO.setProperty_name(rs.getString("PROPERTY_NAME"));
					rowVO.setProperty_value(rs.getString("PROPERTY_VALUE"));
					rowVO.setDescription(rs.getString("DESCRIPTION"));
					propList.add(rowVO);
				} 
				rs.close();
				return propList;
			}catch (SQLException e) {
				throw new RuntimeException(e);
			}
	}

}
