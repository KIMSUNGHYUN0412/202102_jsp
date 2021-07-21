package kr.or.ddit.buyer.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.db.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;

public class BuyerDAOImpl implements BuyerDAO {

	SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();
	
	private static BuyerDAOImpl self;
	
	private BuyerDAOImpl() {}
	
	public static BuyerDAOImpl getInstance() {
		if(self==null) self = new BuyerDAOImpl();
		return self;
	}

	@Override
	public int insertBuyer(BuyerVO buyer) {
		try( 
				SqlSession sqlSession = sqlSessionFactory.openSession();
				){
			BuyerDAO mapper = sqlSession.getMapper(BuyerDAO.class);
			int rowcnt = mapper.insertBuyer(buyer);
			sqlSession.commit();
			return rowcnt;
		}
	}

	@Override
	public int selectTotalRecord(PagingVO<BuyerVO> pagingVO) {
		try( 
				SqlSession sqlSession = sqlSessionFactory.openSession();
				){
			BuyerDAO mapper = sqlSession.getMapper(BuyerDAO.class);
			return mapper.selectTotalRecord(pagingVO);
		}
	}

	@Override
	public List<BuyerVO> selectBuyerList(PagingVO<BuyerVO> pagingVO) {
		try( 
				SqlSession sqlSession = sqlSessionFactory.openSession();
				){
			BuyerDAO mapper = sqlSession.getMapper(BuyerDAO.class);
			return mapper.selectBuyerList(pagingVO); 
		}
	}

	@Override
	public BuyerVO selectBuyer(String buyerId) {
		try( 
				SqlSession sqlSession = sqlSessionFactory.openSession();
				){
			BuyerDAO mapper = sqlSession.getMapper(BuyerDAO.class);
			return mapper.selectBuyer(buyerId); 
		}
	}

	@Override
	public int updateBuyer(BuyerVO buyer) {
		try( 
				SqlSession sqlSession = sqlSessionFactory.openSession();
				){
			BuyerDAO mapper = sqlSession.getMapper(BuyerDAO.class);
			int rowcnt = mapper.updateBuyer(buyer);
			sqlSession.commit();
			return rowcnt;
		}
	}

}
