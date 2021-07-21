package kr.or.ddit.emp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.or.ddit.db.ConnectionFactory;
import kr.or.ddit.vo.EmployeeVO;

public class DeptDAOImpl implements DeptDAO {
	private static DeptDAOImpl self;
	private DeptDAOImpl() {
		super();
	}
	
	public static DeptDAOImpl getInstance() {
		if(self == null)
			self = new DeptDAOImpl();
		return self;
	}

	@Override
	public List<Map<String, Object>> selectDeptList() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  DEPTNO,    DNAME,    LOC ");
		sql.append(" FROM    DEPT ");
		try(
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());	
		){
			ResultSet rs = pstmt.executeQuery();
			List<Map<String, Object>> deptList = new ArrayList<>();
			while(rs.next()) {
				Map<String, Object> recordMap = new HashMap<>();
				deptList.add(recordMap);
				recordMap.put("deptno", rs.getInt("DEPTNO"));
				recordMap.put("dname", rs.getString("DNAME"));
				recordMap.put("loc", rs.getString("LOC"));
			}
			rs.close();
			return deptList;
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
