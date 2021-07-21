package kr.or.ddit.board.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.AttatchVO;
import kr.or.ddit.vo.FreeBoardVO;

public class AttatchDAOImpl implements AttatchDAO {
	
	private static AttatchDAOImpl self;
	
	private AttatchDAOImpl() {}
	public static AttatchDAOImpl getInstance() {
		if(self==null)
			self = new AttatchDAOImpl();
		return self;
	}
	
	private SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();
	
	@Override 
	public int insertAttatches(FreeBoardVO board, SqlSession sqlSession) {
		return sqlSession.insert("kr.or.ddit.board.dao.AttatchDAO.insertAttatches", board);
	} 
  
	@Override 
	public AttatchVO selectAttatch(int attNo) {
		try( 
				SqlSession sqlSession = sqlSessionFactory.openSession();
				){
			AttatchDAO mapper = sqlSession.getMapper(AttatchDAO.class);
			return mapper.selectAttatch(attNo); 
		}
	}

	@Override
	public int deleteAttatches(FreeBoardVO board, SqlSession sqlSession) {
		return sqlSession.delete("kr.or.ddit.board.dao.AttatchDAO.deleteAttatches", board);
	} 

	@Override
	public int deleteAll(int boNo, SqlSession sqlSession) { 
		return sqlSession.delete("kr.or.ddit.board.dao.AttatchDAO.deleteAll", boNo);
	}

	@Override
	public int incrementDownCount(int attNo) {
		try( 
				SqlSession sqlSession = sqlSessionFactory.openSession();
				){
			AttatchDAO mapper = sqlSession.getMapper(AttatchDAO.class);
			return mapper.incrementDownCount(attNo);
		}
	}

}
