package kr.or.ddit.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.ConnectionFactory;
import kr.or.ddit.db.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ZipVO;

public class MemberDAOImpl implements MemberDAO {
	//singleton
	private static MemberDAOImpl self;
	
	private MemberDAOImpl() {
		super();
	}
	 
	public static MemberDAOImpl getInstance() {
		if(self==null)
			self = new MemberDAOImpl();
		return self; 
	}
	 
	private SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();

	@Override
	public MemberVO selectMemberById(String mem_id) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession(); 
		){
			return (MemberVO) sqlSession.selectOne("kr.or.ddit.member.dao.MemberDAO.selectMemberById", mem_id);
		} 
	} 

	@Override
	public int insertMember(MemberVO member) {
		try( 
				SqlSession sqlSession = sqlSessionFactory.openSession();
				){
			MemberDAO mapper = sqlSession.getMapper(MemberDAO.class);
			int rowcnt = mapper.insertMember(member); 
			sqlSession.commit();  
			return rowcnt;   
		}
	}
	
	@Override
	public int selectTotalRecord(PagingVO<MemberVO> pagingVO) {
		try( 
				SqlSession sqlSession = sqlSessionFactory.openSession();
				){
			MemberDAO mapper = sqlSession.getMapper(MemberDAO.class);
			return mapper.selectTotalRecord(pagingVO); 
		}
	}

	@Override
	public List<MemberVO> selectMemberList(PagingVO<MemberVO> pagingVO) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			return sqlSession.selectList("kr.or.ddit.member.dao.MemberDAO.selectMemberList", pagingVO);
		}
		
	}

	@Override
	public MemberVO selectMemberDetail(String mem_id) {
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){  
			// proxy 객체 생성 
			MemberDAO mapper = sqlSession.getMapper(MemberDAO.class);
			System.out.println(mapper.getClass()); 
			return mapper.selectMemberDetail(mem_id);
		}
	}

	@Override
	public int updateMember(MemberVO member) {
		try( 
			//autoCommit 기본값 false
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			MemberDAO mapper = sqlSession.getMapper(MemberDAO.class);
			int rowcnt = mapper.updateMember(member);
			sqlSession.commit();  
			return rowcnt; 
		}
	} 

	@Override
	public int deleteMember(String mem_id) {
		try( 
			SqlSession sqlSession = sqlSessionFactory.openSession();
				){
			MemberDAO mapper = sqlSession.getMapper(MemberDAO.class);
			int rowcnt = mapper.deleteMember(mem_id); 
			sqlSession.commit();
			return rowcnt;
		}
	}

	@Override
	public List<ZipVO> searhZipList() {
		try( 
				SqlSession sqlSession = sqlSessionFactory.openSession();
				){
			MemberDAO mapper = sqlSession.getMapper(MemberDAO.class);
			return mapper.searhZipList(); 
		}
	}

}
