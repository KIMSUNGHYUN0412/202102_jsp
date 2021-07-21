package kr.or.ddit.buyer.service;

import java.util.List;

import kr.or.ddit.buyer.dao.BuyerDAOImpl;
import kr.or.ddit.commons.exception.DataNotFoundException;
import kr.or.ddit.enumtype.ServiceResult;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.PagingVO;

public class BuyerServiceImpl implements BuyerService {
	
	private BuyerDAOImpl dao = BuyerDAOImpl.getInstance();
	
	private static BuyerServiceImpl self;
	private BuyerServiceImpl() {}

	public static BuyerServiceImpl getInstance() {
		if(self==null) self = new BuyerServiceImpl();
		return self;
	}
	
	@Override
	public ServiceResult createBuyer(BuyerVO buyer) {
		int rowcnt = dao.insertBuyer(buyer);
		ServiceResult result = null;
		if(rowcnt > 0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		} 
		return result; 
	}
	
	@Override
	public void retrieveBuyerList(PagingVO<BuyerVO> pagingVO) {
		int totalRecord = dao.selectTotalRecord(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		List<BuyerVO> dataList = dao.selectBuyerList(pagingVO);
		pagingVO.setDataList(dataList);
	}

	@Override
	public BuyerVO retrieveBuyer(String buyerId) {
		BuyerVO buyer = dao.selectBuyer(buyerId);
		if(buyer==null) {
			throw new DataNotFoundException(buyerId);
		}
		return buyer;
	}

	@Override
	public ServiceResult modifyBuyer(BuyerVO buyer) {
		retrieveBuyer(buyer.getBuyerId());
		ServiceResult result = null;
		int rowcnt = dao.updateBuyer(buyer);
		if(rowcnt > 0) { 
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		} 
		return result; 
	}

	
}
