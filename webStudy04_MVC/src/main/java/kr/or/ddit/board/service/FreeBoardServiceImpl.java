package kr.or.ddit.board.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.ddit.board.dao.AttatchDAO;
import kr.or.ddit.board.dao.AttatchDAOImpl;
import kr.or.ddit.board.dao.FreeBoardDAO;
import kr.or.ddit.board.dao.FreeBoardDAOImpl;
import kr.or.ddit.commons.exception.DataNotFoundException;
import kr.or.ddit.db.mybatis.CustomSqlSessionFactoryBuilder;
import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.utils.EncryptUtils;
import kr.or.ddit.vo.AttatchVO;
import kr.or.ddit.vo.FreeBoardVO;
import kr.or.ddit.vo.PagingVO;

public class FreeBoardServiceImpl implements FreeBoardService {
	private FreeBoardDAO boardDAO = FreeBoardDAOImpl.getInstance();
	private AttatchDAO attDAO = AttatchDAOImpl.getInstance();
	
	private static FreeBoardServiceImpl self;
	
	private FreeBoardServiceImpl() {}
	
	public static FreeBoardServiceImpl getInstance() {
		if(self==null) 
			self = new FreeBoardServiceImpl();
		return self;
	}
	
	private SqlSessionFactory sqlSessionFactory = CustomSqlSessionFactoryBuilder.getSqlSessionFactory();
	private File saveFolder = new File("d:/saveFiles");
	

	private String encodingPass(FreeBoardVO board) {
		String plain = board.getBoPass();
		if(plain==null || plain.isEmpty()) return "";
		return EncryptUtils.encryptSha512Base64(plain);
	}
	  
	
	private int processAtts(FreeBoardVO board,SqlSession sqlSession) {
		List<AttatchVO> attatchList = board.getAttatchList();
		if(attatchList==null || attatchList.isEmpty()) return 0; 
		int rowcnt = attDAO.insertAttatches(board, sqlSession);
		if(!saveFolder.exists()) saveFolder.mkdirs(); 
		//2진 데이터 저장    
		try{
			for(AttatchVO attatch : attatchList) {
//				MultipartFile attFile = attatch.getAttFile();
//				String savename = attatch.getAttSavename();
//				File saveFile = new File(saveFolder, savename);
//				attFile.transferTo(saveFile); 
				attatch.saveToBinary(saveFolder);
			} 
			return rowcnt;
		}catch (IOException e) { 
			throw new RuntimeException(e); 
		}
	}
	
	@Override
	public ServiceResult createBoard(FreeBoardVO board) {
		ServiceResult result =  ServiceResult.FAIL;
		//sqlSession open   
		try( 
			SqlSession sqlSession = sqlSessionFactory.openSession();
		){ 
			String encoded = encodingPass(board);
			board.setBoPass(encoded);  
			int rowcnt = boardDAO.insertBoard(board, sqlSession);
			if(rowcnt > 0) {  
				rowcnt += processAtts(board, sqlSession);
				result = ServiceResult.OK; 
				sqlSession.commit();
			} 
			return result;
		} 
	}

	@Override
	public int retrieveBoardCount(PagingVO<FreeBoardVO> pagingVO) {
		return boardDAO.selectTotalRecoard(pagingVO);
	}

	@Override
	public List<FreeBoardVO> retrieveBoardList(PagingVO<FreeBoardVO> pagingVO) {
		return boardDAO.selectBoardList(pagingVO); 
	}

	@Override
	public FreeBoardVO retrieveBoard(int boNo) {
		FreeBoardVO board = boardDAO.selectBoard(boNo);
		if(board==null)
			throw new DataNotFoundException(boNo + "번 게시글 없음");
		boardDAO.incrementHit(boNo);
		return board; 
	}  

	private int deleteAtts(FreeBoardVO board, SqlSession sqlSession) {
		int[] delAttoNos = board.getDelAttNos();
		if(delAttoNos==null || delAttoNos.length==0) return 0;
		String[] saveNames = new String[delAttoNos.length];
		for(int i=0; i<saveNames.length; i++) {
			AttatchVO attatch = attDAO.selectAttatch(delAttoNos[i]);
			saveNames[i] = attatch.getAttSavename(); 
 		}
 		
		int rowcnt = attDAO.deleteAttatches(board, sqlSession);
		
		if(rowcnt > 0) {
			deleteBinaryFiles(saveNames);
		}
		return rowcnt;  
	}
	
	private void deleteBinaryFiles(String...saveNames) {
		if(saveNames==null || saveNames.length==0) return; 
		for(String saveName : saveNames) {
			FileUtils.deleteQuietly(new File(saveFolder, saveName));
		}
	}
	@Override
	public ServiceResult modifyBoard(FreeBoardVO board) {
		FreeBoardVO savedBoard = boardDAO.selectBoard(board.getBoNo());
		if(savedBoard==null)
			throw new DataNotFoundException(board.getBoNo() + "번 게시글 없음");
		ServiceResult result = ServiceResult.FAIL; 
		try( 
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){  
				String encoded = encodingPass(board);
				if(savedBoard.getBoPass().equals(encoded)) {
					int rowcnt = boardDAO.updateBoard(board, sqlSession);
					if(rowcnt > 0) {   
						rowcnt += processAtts(board, sqlSession); 
						rowcnt += deleteAtts(board, sqlSession);
		 				result = ServiceResult.OK; 
						sqlSession.commit(); 
			 		} 
				}else {
					result = ServiceResult.INVALIDPASSWORD;
				}  
				return result;
			} 
	
	}



	
	@Override
	public ServiceResult removeBoard(FreeBoardVO board) {
		int boNo = board.getBoNo();
		FreeBoardVO savedBoard = boardDAO.selectBoard(boNo);
		if(savedBoard==null)
			throw new DataNotFoundException(boNo + "번 게시글 없음");
		ServiceResult result = ServiceResult.FAIL; 
		try( 
				SqlSession sqlSession = sqlSessionFactory.openSession();
			){ 
				String encoded = encodingPass(board); 
				if(savedBoard.getBoPass().equals(encoded)) {
					//이진데이터 저장명
					List<AttatchVO> attatchList = savedBoard.getAttatchList();
					String[] saveNames = null;
					if(!attatchList.isEmpty()) {
						saveNames = new String[attatchList.size()];
						int idx = 0;
						for(AttatchVO attatch: attatchList) {
							saveNames[idx++] = attatch.getAttSavename();
						} 
					}
					//메타데이터 삭제
					int rowcnt = attDAO.deleteAll(boNo, sqlSession);
					//게시글삭제  
					rowcnt += boardDAO.deleteBoard(boNo, sqlSession);
					if(rowcnt > 0) {
						//이진데이터 삭제
						deleteBinaryFiles(saveNames); 
						result = ServiceResult.OK;
						sqlSession.commit();
					} 
				}else {
					result = ServiceResult.INVALIDPASSWORD;
				}  
				return result; 
			} 
	}

	@Override
	public AttatchVO download(int attNo) {
		AttatchVO attatch = attDAO.selectAttatch(attNo);
		if(attatch==null) throw new DataNotFoundException(attNo + "");
		return attatch; 
	} 

	@Override
	public ServiceResult incrementCount(int boNo, CountType type) {
		boardDAO.selectBoard(boNo);
		ServiceResult result = null;
		int rowcnt = 0; 
		System.out.println(type);
		if(type==CountType.RECOMMEND) {
			rowcnt = boardDAO.incrementRec(boNo);
		}else if(type==CountType.REPORT){
			rowcnt = boardDAO.incrementRep(boNo);
		}
		System.out.println("로우카운트 : " + rowcnt); 
		if(rowcnt>0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		
		return result;
	} 

}
