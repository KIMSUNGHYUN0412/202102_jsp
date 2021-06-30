package kr.or.ddit.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import kr.or.ddit.db.ConnectionFactory;
import kr.or.ddit.vo.MemberVO;

public class MemberDAOImpl implements MemberDAO {

	@Override
	public MemberVO selectMemberById(String mem_id) {
//		 		4. 쿼리 객체 생성   
		String sql = "SELECT MEM_ID, MEM_PASS, MEM_NAME FROM MEMBER WHERE MEM_ID = ?";  
		try(
				Connection conn = ConnectionFactory.getConnection(); //커넥션객체 닫으면 resultset도 자동으로 닫힘 
				PreparedStatement pstmt = conn.prepareStatement(sql);
			){
//		 		5. 쿼리 실행     
				   
				pstmt.setString(1, mem_id);
				ResultSet rs = pstmt.executeQuery();
				MemberVO savedMember = null;   
				if(rs.next()){
					savedMember = new MemberVO();
					savedMember.setMem_id(rs.getString("MEM_ID"));
					savedMember.setMem_pass(rs.getString("MEM_PASS")); 
					savedMember.setMem_name(rs.getString("MEM_NAME"));
				}  
 				rs.close();  
				return savedMember; 
			}catch (SQLException e) {
				throw new RuntimeException(e);
			} 
	}

}
