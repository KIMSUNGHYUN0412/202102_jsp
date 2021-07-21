package kr.or.ddit.prod.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.commons.exception.DataNotFoundException;
import kr.or.ddit.db.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.listener.ContextLoaderListener;
import kr.or.ddit.multipart.MultipartFile;
import kr.or.ddit.prod.dao.ProdDAO;
import kr.or.ddit.prod.dao.ProdDAOImpl;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;

public class ProdServiceImpl implements ProdService {
	
	private ProdDAO prodDAO = ProdDAOImpl.getInstance();
	
	/**
	 * 상품의 이미지를 저장
	 * @param prod
	 */
	private void processProdImage(ProdVO prod) {
		MultipartFile prodImage = prod.getProdImage();
		if(prodImage==null) return; //empty여부는 VO에서 수행함
		// 트랜잭션 관리 여부 확인용 코드
//		if(1==1) throw new RuntimeException("강제발생예외");
		
		try(
			InputStream is = prodImage.getInputStream();
		){
			File saveFolder = ContextLoaderListener.prodImages;
			String savename = prod.getProdImg();
			File saveFile = new File(saveFolder, savename);
			FileUtils.copyInputStreamToFile(is, saveFile); 
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();
	// 차후에 spring을 이용하면, AOP방법론에 따라 Platform based transaction Manager를 이용하여 해결
	@Override
	public ServiceResult createProd(ProdVO prod) {
//		--> 트랜잭션 시작, sqlSession open
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			int rowcnt = prodDAO.insertProd(prod, sqlSession);
			// 2진 데이터 저장, middle tier 
			processProdImage(prod);
			ServiceResult result = null;
			if(rowcnt > 0) {
				result = ServiceResult.OK;
//		--> 트랜잭션 종료, sqlSession commit
				sqlSession.commit();
			}else {
				result = ServiceResult.FAIL;
			} 
			return result;
		}
	} 

	@Override
	public void retrieveProdList(PagingVO<ProdVO> pagingVO) {
		int totalRecord = prodDAO.selectTotalRecord(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		List<ProdVO> prodList = prodDAO.selectProdList(pagingVO);
		pagingVO.setDataList(prodList);  

	}

	@Override
	public ProdVO retrieveProd(String prodId) {
		ProdVO prod = prodDAO.selectProd(prodId);
		if(prod==null)   
			throw new DataNotFoundException(prodId);
		return prod;
	}

	@Override
	public ServiceResult modifyProd(ProdVO prod) {
		retrieveProd(prod.getProdId());
		try(
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){
			int rowcnt = prodDAO.updateProd(prod, sqlSession); 
			// 2진 데이터 저장, middle tier
			processProdImage(prod); 
			ServiceResult result = null; 
			if(rowcnt > 0) {
				result = ServiceResult.OK;
				sqlSession.commit();
			}else {
				result = ServiceResult.FAIL;
			}
			return result;
		}
	}

}
