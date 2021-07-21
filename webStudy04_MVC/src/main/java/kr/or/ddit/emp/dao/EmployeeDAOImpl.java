package kr.or.ddit.emp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.or.ddit.db.ConnectionFactory;
import kr.or.ddit.vo.EmployeeVO;
import kr.or.ddit.vo.MemberVO;

public class EmployeeDAOImpl implements EmployeeDAO {

	private static EmployeeDAOImpl self;
	private EmployeeDAOImpl() {
		super();
	}
	
	public static EmployeeDAOImpl getInstance() {
		if(self == null)
			self = new EmployeeDAOImpl();
		return self;
	}

	@Override
	public int insertEmployee(EmployeeVO employee) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO EMP ( EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO) ");
		sql.append("VALUES ((SELECT MAX(EMPNO) + 1 FROM EMP), ?, ?, ?, TO_DATE(SYSDATE,'yyyy-mm-dd'), ?, ?, ?)");
		try (Connection conn = ConnectionFactory.getConnection(); 
			PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
			int i = 1;
			pstmt.setString(i++, employee.getEname());
			pstmt.setString(i++, employee.getJob());
			pstmt.setInt(i++, employee.getMgr());
			pstmt.setInt(i++, employee.getSal());
			pstmt.setInt(i++, employee.getComm());
			pstmt.setInt(i++, employee.getEmpno());
//		 	5. 쿼리 실행
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<EmployeeVO> selectEmployeeList(Map<String, Object> pMap) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT                                  ");
		sql.append("     EMPNO,    ENAME,    JOB,            ");
		sql.append("     MGR,    HIREDATE,    SAL,           ");
		sql.append("     COMM,    A.DEPTNO, DNAME            ");
		sql.append("   , (SELECT DECODE(COUNT(*), 0, '1', '0') FROM EMP C WHERE C.MGR = A.EMPNO ) AS LEAF            ");
		sql.append(" FROM    EMP A LEFT OUTER JOIN DEPT B    ");
		sql.append("         ON(A.DEPTNO = B.DEPTNO)         ");
		if(pMap!=null && pMap.containsKey("mgr")) {
			sql.append(" WHERE MGR = ?                       ");
		}else {
			sql.append(" WHERE MGR IS NULL                       ");
		}
		
		
		try(
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());	
		){
			if(pMap!=null && pMap.containsKey("mgr")) {
				pstmt.setInt(1, (Integer)pMap.get("mgr"));
			}
			
			ResultSet rs = pstmt.executeQuery();
			
			List<EmployeeVO> empList = new ArrayList<>();
			while(rs.next()) {
				EmployeeVO empVO = EmployeeVO.builder()
								.empno(rs.getInt("EMPNO"))
								.ename(rs.getString("ENAME"))
								.job(rs.getString("JOB"))
								.mgr(rs.getInt("MGR"))
								.hiredate(rs.getString("HIREDATE"))
								.sal(rs.getInt("SAL"))
								.comm(rs.getInt("COMM"))
								.deptno(rs.getInt("DEPTNO"))
								.dname(rs.getString("DNAME"))
								.leaf(rs.getBoolean("LEAF"))
								.build();
				empList.add(empVO);
				
			}
			rs.close();
			return empList;
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public EmployeeVO selectEmployee(int empno) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT                                  ");
		sql.append("     EMPNO,    ENAME,    JOB,            ");
		sql.append("     MGR,    HIREDATE,    SAL,           ");
		sql.append("     COMM,    A.DEPTNO, DNAME            ");
		sql.append("   , (SELECT DECODE(COUNT(*), 0, '1', '0') FROM EMP C WHERE C.MGR = A.EMPNO ) AS LEAF            ");
		sql.append(" FROM    EMP A LEFT OUTER JOIN DEPT B    ");
		sql.append("         ON(A.DEPTNO = B.DEPTNO)         ");
		sql.append(" WHERE EMPNO = ?                       ");
		
		
		try(
			Connection conn = ConnectionFactory.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());	
		){
			pstmt.setInt(1, empno);
			
			ResultSet rs = pstmt.executeQuery();
			EmployeeVO empVO = null;
			if(rs.next()) {
				empVO = EmployeeVO.builder()
								.empno(rs.getInt("EMPNO"))
								.ename(rs.getString("ENAME"))
								.job(rs.getString("JOB"))
								.mgr(rs.getInt("MGR"))
								.hiredate(rs.getString("HIREDATE"))
								.sal(rs.getInt("SAL"))
								.comm(rs.getInt("COMM"))
								.deptno(rs.getInt("DEPTNO"))
								.dname(rs.getString("DNAME"))
								.leaf(rs.getBoolean("LEAF"))
								.build();
				
			}
			rs.close();
			return empVO;
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int updateEmployee(EmployeeVO employee) {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE EMP       ");
		sql.append(" SET              ");
		sql.append(" JOB = ?,         ");
		sql.append(" SAL = ?,         ");
		sql.append(" COMM = ?         ");
		sql.append(" WHERE EMPNO = ?  ");
		try (Connection conn = ConnectionFactory.getConnection(); 
			PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
			int i = 1;
			pstmt.setString(i++, employee.getJob());
			pstmt.setInt(i++, employee.getSal());
			pstmt.setInt(i++, employee.getComm());
			pstmt.setInt(i++, employee.getEmpno());
//		 	5. 쿼리 실행
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int deleteEmployee(int empno) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM EMP       ");
		sql.append(" WHERE EMPNO = ?  ");
		try (Connection conn = ConnectionFactory.getConnection(); 
			PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
			pstmt.setInt(1, empno);
//		 	5. 쿼리 실행
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
