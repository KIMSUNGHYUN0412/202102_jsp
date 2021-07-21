package kr.or.ddit.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.FreeBoardVO;
import kr.or.ddit.vo.PagingVO;

public class FreeBoardDAOImpl implements FreeBoardDAO {
	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();
	
	private static FreeBoardDAOImpl self;
	
	private FreeBoardDAOImpl() {}
	
	public static FreeBoardDAOImpl getInstance() {
		if(self==null)
			self = new FreeBoardDAOImpl();
		return self;
	}
 
	@Override
	public int insertBoard(FreeBoardVO board, SqlSession sqlSession) { 
		return sqlSession.insert("kr.or.ddit.board.dao.FreeBoardDAO.insertBoard" ,board);
	}  

	@Override
	public int selectTotalRecoard(PagingVO<FreeBoardVO> pagingVO) {
		try( 
				SqlSession sqlSession = sqlSessionFactory.openSession();
				){
			FreeBoardDAO mapper = sqlSession.getMapper(FreeBoardDAO.class);
			return mapper.selectTotalRecoard(pagingVO);
		}
	} 

	@Override
	public List<FreeBoardVO> selectBoardList(PagingVO<FreeBoardVO> pagingVO) {
		try( 
				SqlSession sqlSession = sqlSessionFactory.openSession();
				){
			FreeBoardDAO mapper = sqlSession.getMapper(FreeBoardDAO.class);
			return mapper.selectBoardList(pagingVO);
		}
	}

	@Override
	public FreeBoardVO selectBoard(int boNo) {
		try( 
				SqlSession sqlSession = sqlSessionFactory.openSession();
				){
			FreeBoardDAO mapper = sqlSession.getMapper(FreeBoardDAO.class);
			return mapper.selectBoard(boNo); 
		}
	}

	@Override
	public int updateBoard(FreeBoardVO board, SqlSession sqlSession) {
		return sqlSession.update("kr.or.ddit.board.dao.FreeBoardDAO.updateBoard", board);
	}

	@Override
	public int deleteBoard(int boNo, SqlSession sqlSession) {
		return sqlSession.delete("kr.or.ddit.board.dao.FreeBoardDAO.deleteBoard", boNo);
	}

	@Override
	public int incrementHit(int boNo) {
		try( 
				SqlSession sqlSession = sqlSessionFactory.openSession();
				){
			FreeBoardDAO mapper = sqlSession.getMapper(FreeBoardDAO.class);
			int cnt = mapper.incrementHit(boNo); 
			sqlSession.commit();
			return cnt; 
		}
	}

	@Override
	public int incrementRec(int boNo) {
		try( 
				SqlSession sqlSession = sqlSessionFactory.openSession();
				){
			FreeBoardDAO mapper = sqlSession.getMapper(FreeBoardDAO.class);
			int cnt =  mapper.incrementRec(boNo);
			sqlSession.commit();
			return cnt; 
		}
	} 

	@Override
	public int incrementRep(int boNo) {
		try( 
				SqlSession sqlSession = sqlSessionFactory.openSession();
				){
			FreeBoardDAO mapper = sqlSession.getMapper(FreeBoardDAO.class);
			int cnt = mapper.incrementRep(boNo); 
			sqlSession.commit();
			return cnt; 
		}
	}

}
